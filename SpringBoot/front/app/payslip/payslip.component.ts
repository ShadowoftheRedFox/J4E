import { AfterViewInit, Component, inject, Injectable, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { Employee } from '../../models/APIModels';
import { ApiService } from '../../services/api.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from "@angular/material/icon";
import { Subject } from 'rxjs';
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatSortModule } from "@angular/material/sort"
import { MatMenuModule } from "@angular/material/menu"
import { MatInputModule } from "@angular/material/input"
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopupService } from '../../services/popup.service';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

type Columns = "id" | "firstName" | "lastName" | "username" | "action";

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
    selector: 'app-payslip',
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
    templateUrl: './payslip.component.html',
    styleUrl: './payslip.component.scss',
    providers: [{ provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }]
})
export class PayslipComponent implements AfterViewInit {
    readonly displayedColumns: Columns[] = ['id', 'username', 'firstName', 'lastName', 'action'];

    private readonly api = inject(ApiService);
    private readonly auth = inject(AuthService);
    private readonly popup = inject(PopupService);
    private readonly dialog = inject(MatDialog);
    private readonly router = inject(Router);
    private readonly route = inject(ActivatedRoute);

    dataSource = new MatTableDataSource<Employee>([]);

    allEmployees: Employee[] = [];
    filteredEmployees: Employee[] = [];

    filterControl = new FormControl("");

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
    }

    constructor() {
        this.api.employee.getAll().subscribe(res => {
            if (res == null || !Array.isArray(res)) { return; }
            this.allEmployees = res;
            this.dataSource.data = res;
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
            this.dataSource.data = this.filteredEmployees;
        });
    }

    showPayslip(id: number) {
        this.router.navigate([id], { relativeTo: this.route });
    }
}
