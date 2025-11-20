import { AfterViewInit, Component, inject, Injectable, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { Project } from '../../models/APIModels';
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
import { ProjectFormComponent } from './project-form/project-form.component';


type Columns = "id" | "name" | "status" /*| "projects"*/ | "action";

@Injectable()
export class CustomPaginatorIntl implements MatPaginatorIntl {
    changes = new Subject<void>();

    // For internationalization, the `$localize` function from
    // the `@angular/localize` package can be used.
    firstPageLabel = `Première page`;
    lastPageLabel = `Dernière page`;

    itemsPerPageLabel = `Projets par page:`;

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
    selector: 'app-project',
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
    templateUrl: './project.component.html',
    styleUrl: './project.component.scss'
})
export class ProjectComponent implements AfterViewInit {
    readonly displayedColumns: Columns[] = ['id', 'name', 'status', 'action'];

    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);
    readonly dialog = inject(MatDialog);

    allProjects: Project[] = [];
    sortedProjects: Project[] = [];
    filteredProjects: Project[] = [];

    dataSource = new MatTableDataSource<Project>([]);

    filterControl = new FormControl("");

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    constructor() {
        this.updateProjects();

        this.filterControl.valueChanges.subscribe((c) => {
            if (!c || c.length == 0) {
                this.dataSource.data = this.allProjects;
                return;
            }
            this.filteredProjects = [];
            this.allProjects.forEach(u => {
                if (
                    ("" + u.id).includes(c) ||
                    u.name.includes(c) ||
                    u.status.includes(c)
                ) {
                    this.filteredProjects.push(u);
                }
            });
            this.sortedProjects = this.filteredProjects;
            this.dataSource.data = this.filteredProjects;
        });
    }

    updateProjects() {
        this.api.project.getAll().subscribe(res => {
            if (Array.isArray(res)) {
                this.allProjects = res;
                this.sortedProjects = res;
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
        const data = this.allProjects.slice();
        if (!sort.active || sort.direction === '') {
            this.sortedProjects = data;
            return;
        }

        this.sortedProjects = data.sort((a, b) => {
            const isAsc = sort.direction === "asc";

            switch (sort.active as Columns) {
                case "id":
                    return this.compare(a.id, b.id, isAsc);
                case "name":
                    return this.compare(a.name, b.name, isAsc);
                case "status":
                    return this.compare(a.status, b.status, isAsc);
                default:
                    return 0;
            }
        })
    }

    add() {
        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: ProjectFormComponent,
                title: "Création d'un projet",
                data: { project: null },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            console.warn(res);
            if (res) {
                this.updateProjects();
            }
        });
    }

    edit(id: number) {
        const e = this.allProjects.find(v => v.id == id);
        if (e == undefined) {
            this.popup.openSnackBar({ message: "Projet inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: ProjectFormComponent,
                data: { project: e },
                title: "Modification de " + e.name,
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.updateProjects();
            }
        });
    }

    delete(id: number) {
        const p = this.allProjects.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Projet inconnu" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                title: "Suppression de " + p.name,
                btnNotOk: "Annuler",
                btnOk: "Effacer",
                warn: true,
                text: "Êtes vous sur de vouloir effacer cet projet?"
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.api.project.delete(id).subscribe({
                    next: () => {
                        this.popup.openSnackBar({ message: "Projet effacé" });
                        this.updateProjects();
                    },
                    error: () => {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    }
                });
            }
        });
    }
}
