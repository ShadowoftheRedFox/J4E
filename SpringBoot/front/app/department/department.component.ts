import { AfterViewInit, Component, inject, Injectable, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { Department } from '../../models/APIModels';
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
import { PopupService } from '../../services/popup.service';
import { DialogComponent, DialogDataType } from '../../shared/dialog/dialog.component';
import { DepartmentFormComponent } from './department-form/department-form.component';

type Columns = "id" | "name" | "employees" | "action";

@Injectable()
export class CustomPaginatorIntl implements MatPaginatorIntl {
    changes = new Subject<void>();

    // For internationalization, the `$localize` function from
    // the `@angular/localize` package can be used.
    firstPageLabel = `Première page`;
    lastPageLabel = `Dernière page`;

    itemsPerPageLabel = `Départements par page:`;

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
    selector: 'app-department',
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
    templateUrl: './department.component.html',
    styleUrl: './department.component.scss',
    providers: [{ provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }],

})
export class DepartmentComponent implements AfterViewInit {
    readonly displayedColumns: Columns[] = ['id', 'name', 'employees', 'action'];

    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);
    private readonly dialog = inject(MatDialog);

    allDepartments: Department[] = [];
    sortedDepartments: Department[] = [];
    filteredDepartments: Department[] = [];

    dataSource = new MatTableDataSource<Department>([]);

    filterControl = new FormControl("");

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    constructor() {
        this.updateDepartments();

        this.filterControl.valueChanges.subscribe((c) => {
            if (!c || c.length == 0) {
                this.dataSource.data = this.allDepartments;
                return;
            }
            this.filteredDepartments = [];
            this.allDepartments.forEach(u => {
                if (
                    ("" + u.id).includes(c) ||
                    u.name.includes(c)
                ) {
                    this.filteredDepartments.push(u);
                }
            });
            this.sortedDepartments = this.filteredDepartments;
            this.dataSource.data = this.filteredDepartments;
        });
    }

    updateDepartments() {
        this.api.department.getAll().subscribe(res => {
            if (Array.isArray(res)) {
                this.allDepartments = res;
                this.sortedDepartments = res;
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
        const data = this.allDepartments.slice();
        if (!sort.active || sort.direction === '') {
            this.sortedDepartments = data;
            return;
        }

        this.sortedDepartments = data.sort((a, b) => {
            const isAsc = sort.direction === "asc";

            switch (sort.active as Columns) {
                case "id":
                    return this.compare(a.id, b.id, isAsc);
                case "name":
                    return this.compare(a.name, b.name, isAsc);
                default:
                    return 0;
            }
        })
    }

    add() {
        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: DepartmentFormComponent,
                title: "Création d'un département",
                data: { department: null },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            console.warn(res);
            this.updateDepartments();
        });
    }

    edit(id: number) {
        const p = this.allDepartments.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Département inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: DepartmentFormComponent,
                data: { department: p },
                title: "Modification de " + p.name,
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.updateDepartments();
            }
        });
    }

    delete(id: number) {
        const p = this.allDepartments.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Département inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                title: "Suppression de " + p.name,
                btnNotOk: "Annuler",
                btnOk: "Effacer",
                warn: true,
                text: "Êtes vous sur de vouloir effacer ce département?"
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.api.department.delete(id).subscribe({
                    next: () => {
                        this.popup.openSnackBar({ message: "Département effacé" });
                        this.updateDepartments();
                    },
                    error: () => {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    }
                });
            }
        });
    }

    editEmployees(id: number) {
        const p = this.allDepartments.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Département inconnu" });
            return;
        }


    }
}
