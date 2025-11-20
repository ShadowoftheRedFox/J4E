import { AfterViewInit, Component, computed, inject, Injectable, signal, ViewChild } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Employee, EmployeePermission, EmployeeRank } from '../../../models/APIModels';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input"
import { MatMenuModule } from "@angular/material/menu"
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSort, MatSortModule, Sort } from "@angular/material/sort"
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { PopupService } from '../../../services/popup.service';
import { Subject } from 'rxjs';
import { MatCheckboxModule } from '@angular/material/checkbox';

type Columns = "id" | "selection" | "firstName" | "lastName" | "username";

interface Task {
    employee: Employee,
    checked: boolean
}

export interface EmployeeSelectionDataType {
    title?: string,
    onlyOne?: boolean,
    alreadyIn?: number[]
}

@Injectable()
export class CustomPaginatorIntl implements MatPaginatorIntl {
    changes = new Subject<void>();

    // For internationalization, the `$localize` function from
    // the `@angular/localize` package can be used.
    firstPageLabel = `Première page`;
    lastPageLabel = `Dernière page`;

    itemsPerPageLabel = `Employés par page:`;

    // You can set labels to an arbitrary string too, or dynamically compute
    // it through other third-party internationalization libraries.
    nextPageLabel = 'Page suivante';
    previousPageLabel = 'Page précédente';

    getRangeLabel(page: number, pageSize: number, length: number): string {
        if (length === 1) {
            return `Page unique`;
        }
        const amountPages = Math.ceil(length / pageSize);
        return `Page ${page + 1} sur ${amountPages}`;
    }
}
@Component({
    selector: 'app-select-employees',
    imports: [
        MatTableModule,
        MatPaginatorModule,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatSortModule,
        MatMenuModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatCheckboxModule,
        MatDialogModule
    ],
    templateUrl: './select-employees.component.html',
    styleUrl: './select-employees.component.scss',
    providers: [{ provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }],
})
export class SelectEmployeesComponent implements AfterViewInit {
    readonly displayedColumns: Columns[] = ['id', 'selection', 'username', 'firstName', 'lastName'];

    readonly data = inject<EmployeeSelectionDataType>(MAT_DIALOG_DATA);
    private dialogRef = inject(MatDialogRef<SelectEmployeesComponent, Employee[]>);
    private firestSet = true;

    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);
    readonly dialog = inject(MatDialog);

    allEmployees: Employee[] = [];
    sortedEmployees: Employee[] = [];
    filteredEmployees: Employee[] = [];

    selectedEmployees = signal<{ tasks: Task[] }>({ tasks: [] });

    departments: Map<number, string> = new Map<number, string>();
    readonly roles = EmployeeRank;
    readonly permissions = EmployeePermission;

    dataSource = new MatTableDataSource<Employee>([]);

    filterControl = new FormControl("");
    departmentControl = new FormControl("");
    roleControl = new FormControl("");
    permissionControl = new FormControl("");

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }


    constructor() {
        if (!this.data) this.data = {};
        if (!this.data.title) this.data.title = "Selection d'employés";
        if (!this.data.onlyOne) this.data.onlyOne = false;
        if (!this.data.alreadyIn) this.data.alreadyIn = [];

        this.updateEmployees();

        this.api.department.getAll().subscribe(res => {
            if (res == null || !Array.isArray(res)) { return; }
            res.forEach(d => {
                this.departments.set(d.id, d.name);
            });
        });

        this.filterControl.valueChanges.subscribe(() => {
            this.updateFilter();
        });

        this.departmentControl.valueChanges.subscribe(() => {
            this.updateFilter();
        });

        this.roleControl.valueChanges.subscribe(() => {
            this.updateFilter();
        });

        this.permissionControl.valueChanges.subscribe(() => {
            this.updateFilter();
        });
    }

    updateEmployees() {
        this.api.employee.getAll().subscribe(res => {
            if (Array.isArray(res)) {
                this.allEmployees = res;
                this.sortedEmployees = res;
                this.dataSource.data = res;
                this.filteredEmployees = res;
                const tasks: Task[] = [];
                // first set of employees
                if (this.firestSet === true) {
                    this.firestSet = false;
                    this.allEmployees.forEach(e => {
                        tasks.push({
                            employee: e,
                            checked: this.data.alreadyIn?.includes(e.id) || false
                        });
                    });
                } else {
                    this.allEmployees.forEach(e => {
                        tasks.push({
                            employee: e,
                            checked: this.selectedEmployees().tasks.find(t => t.employee.id)?.checked || false
                        });
                    });
                }
                this.selectedEmployees.set({ tasks: tasks });
            }
        });
    }

    updateFilter() {
        const filterValue = this.filterControl.value;
        const departmentValue = this.departmentControl.value;
        const roleValue = this.roleControl.value;
        const permissionValue = this.permissionControl.value;

        this.filteredEmployees = this.allEmployees;

        if (filterValue && filterValue.length > 0) {
            this.filteredEmployees = this.filteredEmployees.filter(e =>
                ("" + e.id).includes(filterValue) ||
                e.firstName.includes(filterValue) ||
                e.lastName.includes(filterValue) ||
                e.username.includes(filterValue));
        }

        if (departmentValue && departmentValue.length > 0) {
            this.filteredEmployees = this.filteredEmployees.filter(e =>
                this.departments.get(e.department) == departmentValue
            );
        }

        if (roleValue && roleValue.length > 0) {
            this.filteredEmployees = this.filteredEmployees.filter(e =>
                e.ranks.includes(roleValue as EmployeeRank)
            );
        }

        if (permissionValue && permissionValue.length > 0) {
            this.filteredEmployees = this.filteredEmployees.filter(e =>
                e.permissions.includes(permissionValue as EmployeePermission)
            );
        }

        this.sortedEmployees = this.filteredEmployees;
        this.dataSource.data = this.filteredEmployees;
    }

    private compare(a: number | string, b: number | string, isAsc: boolean) {
        return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }

    sortData(sort: Sort) {
        const data = this.filteredEmployees.slice();
        if (!sort.active || sort.direction === '') {
            this.sortedEmployees = data;
            return;
        }

        this.sortedEmployees = data.sort((a, b) => {
            const isAsc = sort.direction === "asc";

            switch (sort.active as Columns) {
                case "id":
                    return this.compare(a.id, b.id, isAsc);
                case "username":
                    return this.compare(a.username, b.username, isAsc);
                case "firstName":
                    return this.compare(a.firstName, b.firstName, isAsc);
                case "lastName":
                    return this.compare(a.lastName, b.lastName, isAsc);
                default:
                    return isAsc ? 0 : 1;
            }
        });
    }

    isSelected(id: number) {
        return this.selectedEmployees().tasks.find((v) => v.employee.id === id)?.checked || false;
    }

    readonly partiallyChecked = computed(() => {
        const task = this.selectedEmployees().tasks || [];
        let every = true;
        let some = false;
        task.forEach(t => {
            // if (this.filteredEmployees.includes(t.employee)) {
            if (this.filteredEmployees.find((v) => v.id == t.employee.id) != undefined) {
                if (t.checked) {
                    some = true;
                } else {
                    every = false;
                }
            }
        });
        return some && !every;
        // return task.some(t => t.checked) && !task.every(t => t.checked);
    });

    readonly allChecked = computed(() => {
        const task = this.selectedEmployees().tasks || [];
        let every = true;
        for (const t in task) {
            // if (!task[t].checked && this.filteredEmployees.includes(task[t].employee)) {
            if (!task[t].checked && this.filteredEmployees.find((v) => v.id == task[t].employee.id)) {
                every = false;
                break;
            }
        }
        return every;
        // return task.every(t => t.checked);
    })

    update(checked: boolean, id?: number) {
        this.selectedEmployees.update(task => {
            // if id is undefined, that means all tasks in the filtered array
            if (id === undefined) {
                task.tasks.forEach(e => {
                    if (this.filteredEmployees.includes(e.employee)) {
                        e.checked = checked;
                    }
                });
            } else {
                if (this.data.onlyOne) {
                    task.tasks.forEach((v) => {
                        if (v.employee.id == id) {
                            v.checked = checked;
                        } else {
                            v.checked = false;
                        }
                    })
                } else {
                    task.tasks.find((v) => v.employee.id == id)!.checked = checked
                }
            }
            return { ...task };
        });
    }

    submit() {
        this.dialogRef.close(this.selectedEmployees().tasks.filter(t => t.checked === true).map((v) => v.employee));
    }
}
