import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { provideNativeDateAdapter } from '@angular/material/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Employee, Payslip } from '../../../models/APIModels';
import { ApiService } from '../../../services/api.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from "@angular/material/datepicker"
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent, DialogDataType } from '../../../shared/dialog/dialog.component';
import { PayslipFormComponent } from '../payslip-form/payslip-form.component';
import { PopupService } from '../../../services/popup.service';
import { PayslipItemComponent } from "./payslip-item/payslip-item.component";

@Component({
    selector: 'app-visualization',
    imports: [
        RouterLink,
        MatFormFieldModule,
        MatDatepickerModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        PayslipItemComponent
    ],
    templateUrl: './visualization.component.html',
    styleUrl: './visualization.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [provideNativeDateAdapter()],
})
export class VisualizationComponent {
    private readonly api = inject(ApiService);
    private readonly route = inject(ActivatedRoute);
    private readonly popup = inject(PopupService);
    readonly dialog = inject(MatDialog);

    employee_id = signal(0);
    employee: Employee | null = null;
    allPayslips: Payslip[] = [];
    filteredPayslips: Payslip[] = [];

    constructor() {
        this.route.paramMap.subscribe(res => {
            this.employee_id.set(Number(res.get("id")));
            if (!isNaN(this.employee_id())) {
                this.api.employee.get(this.employee_id()).subscribe({
                    next: (res) => {
                        this.employee = res;
                        this.updatePayslips();
                    },
                    error: () => {
                        this.employee_id.set(-1);
                    }
                })
            }
        });


        this.range.valueChanges.subscribe(res => {
            if (res.end == null || res.start == null) { return; }
            this.updateSelection(res.start, res.end);
        });
    }

    updatePayslips() {
        this.api.payslip.getAllOfEmployee(this.employee_id()).subscribe({
            next: (res) => {
                this.allPayslips = res;

                this.range.markAllAsTouched();
                this.range.markAllAsDirty();
            }
        });
    }

    updateSelection(start: Date, end: Date) {
        this.filteredPayslips = [];
        this.allPayslips.forEach(p => {
            if (p.date >= start.getTime() && p.date <= end.getTime()) {
                this.filteredPayslips.push(p);
            }
        });
    }

    readonly range = new FormGroup({
        start: new FormControl<Date | null>(null),
        end: new FormControl<Date | null>(null),
    });

    add() {
        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: PayslipFormComponent,
                title: "Création d'une paie",
                data: { payslip: null, employeeId: this.employee_id },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            if (res) {
                this.updatePayslips();
            }
        });
    }

    edit(id: number) {
        const p = this.allPayslips.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Paie inconnue" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: PayslipFormComponent,
                title: "Modification d'une paie",
                data: { payslip: p, employeeId: this.employee_id },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(res => {
            console.log(res);
            if (res) {
                this.updatePayslips();
            }
        });
    }

    delete(id: number) {
        const p = this.allPayslips.find(v => v.id == id);
        if (p == undefined) {
            this.popup.openSnackBar({ message: "Paie inconnue" });
            return;
        }

        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                title: "Suppression de la paie " + p.id + " de " + this.employee?.firstName + " " + this.employee?.lastName,
                btnNotOk: "Annuler",
                btnOk: "Effacer",
                warn: true,
                text: "Êtes vous sur de vouloir effacer cet feuille de paie?"
            }
        });
        ref.afterClosed().subscribe(res => {
            console.log(res);
            if (res) {
                this.api.payslip.delete(id).subscribe({
                    next: () => {
                        this.popup.openSnackBar({ message: "Paie effacée" });
                        this.updatePayslips();
                    },
                    error: () => {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    }
                });
            }
        });
    }
}
