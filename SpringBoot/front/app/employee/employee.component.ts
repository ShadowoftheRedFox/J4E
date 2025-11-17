import { AfterViewInit, Component, inject, Injectable, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { Employee } from '../../models/APIModels';
import { ApiService } from '../../services/api.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from "@angular/material/icon";
import { Subject } from 'rxjs';
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatSort, MatSortModule, Sort } from "@angular/material/sort"
import { MatMenuModule } from "@angular/material/menu"
import { MatInputModule } from "@angular/material/input"
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent, DialogDataType } from '../../shared/dialog/dialog.component';
import { EmployeeFormComponent } from './employee-form/employee-form.component';
import { PopupService } from '../../services/popup.service';
import { AuthService } from '../../services/auth.service';

type Columns = "id" | "firstName" | "lastName" | "username" | "action" | "department" | "ranks" | "permissions";

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
    selector: 'app-employee',
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
        MatInputModule
    ],
    templateUrl: './employee.component.html',
    styleUrl: './employee.component.scss',
    providers: [{ provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }],
})
export class EmployeeComponent implements AfterViewInit {
    readonly displayedColumns: Columns[] = ['id', 'username', 'firstName', 'lastName', 'ranks', 'department', 'action'];

    private readonly api = inject(ApiService);
    private readonly auth = inject(AuthService);
    private readonly popup = inject(PopupService);
    readonly dialog = inject(MatDialog);

    allEmployees: Employee[] = [];
    sortedEmployees: Employee[] = [];
    filteredEmployees: Employee[] = [];
    departments: Map<number, string> = new Map<number, string>();

    dataSource = new MatTableDataSource<Employee>([]);

    filterControl = new FormControl("");

    ownId = this.auth.user?.id || 0;

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    constructor() {
        this.updateEmployees();

        this.api.department.getAll().subscribe(res => {
            if (res == null || !Array.isArray(res)) { return; }
            res.forEach(d => {
                this.departments.set(d.id, d.name);
            });
        });

        this.filterControl.valueChanges.subscribe((c) => {
            if (!c || c.length == 0) {
                this.dataSource.data = this.allEmployees;
                return;
            }
            this.filteredEmployees = [];
            this.allEmployees.forEach(u => {
                if (
                    ("" + u.id).includes(c) ||
                    u.firstName.includes(c) ||
                    u.lastName.includes(c) ||
                    u.username.includes(c)
                ) {
                    this.filteredEmployees.push(u);
                }
            });
            this.sortedEmployees = this.filteredEmployees;
            this.dataSource.data = this.filteredEmployees;
        });
    }

    updateEmployees() {
        this.api.employee.getAll().subscribe(res => {
            if (Array.isArray(res)) {
                this.allEmployees = res;
                this.sortedEmployees = res;
                this.dataSource.data = res;
            }
        });
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    private compare(a: number | string, b: number | string, isAsc: boolean) {
        return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }

    sortData(sort: Sort) {
        const data = this.allEmployees.slice();
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
                case "department":
                    return this.compare(this.departments.get(a.department) || "Aucun", this.departments.get(b.department) || "Aucun", isAsc);
                default:
                    return isAsc ? 0 : 1;
            }
        })
    }

    add() {
        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: EmployeeFormComponent,
                title: "Création d'un employé",
                data: { employee: null },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.updateEmployees();
            }
        });
    }

    edit(id: number) {
        const e = this.allEmployees.find(v => v.id == id);
        if (e == undefined) {
            this.popup.openSnackBar({ message: "Employé inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: EmployeeFormComponent,
                data: { employee: e },
                title: "Modification de " + e.firstName + " " + e.lastName,
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.updateEmployees();
            }
        });
    }

    delete(id: number) {
        const e = this.allEmployees.find(v => v.id == id);
        if (e == undefined) {
            this.popup.openSnackBar({ message: "Employé inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                title: "Suppression de " + e.firstName + " " + e.lastName,
                btnNotOk: "Annuler",
                btnOk: "Effacer",
                warn: true,
                text: "Êtes vous sur de vouloir effacer cet employé?"
            }
        });
        ref.afterClosed().subscribe(res => {
            console.log(res);
            if (res) {
                this.api.employee.delete(id).subscribe({
                    next: () => {
                        this.popup.openSnackBar({ message: "Employé effacé" });
                        this.updateEmployees();
                    },
                    error: () => {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    }
                });
            }
        });
    }
}
