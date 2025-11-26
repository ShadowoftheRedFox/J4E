import { ChangeDetectorRef, Component, inject, signal, WritableSignal } from '@angular/core';
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
import { AuthService } from '../../../services/auth.service';

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
    providers: [provideNativeDateAdapter()]
})
export class VisualizationComponent {
    private readonly api = inject(ApiService);
    private readonly route = inject(ActivatedRoute);
    private readonly popup = inject(PopupService);
    private readonly dialog = inject(MatDialog);
    private readonly ref = inject(ChangeDetectorRef);
    readonly auth = inject(AuthService);

    employee_id = signal(0);
    employee: Employee | null = null;
    allPayslips: Payslip[] = [];
    filteredPayslips: Payslip[] = [];
    ploof: WritableSignal<Payslip[]> = signal([]);

    readonly range = new FormGroup({
        start: new FormControl<Date | null>(null),
        end: new FormControl<Date | null>(null),
    });

    constructor() {
        this.route.paramMap.subscribe(res => {
            this.employee_id.set(Number(res.get("id")));
            if (!isNaN(this.employee_id())) {
                this.api.employee.get(this.employee_id()).subscribe({
                    next: (res) => {
                        this.employee = res;
                        this.updatePayslips();
                        this.updateSelection(null, null);
                    },
                    error: () => {
                        this.employee_id.set(-1);
                    }
                });
            }
        });

        this.range.valueChanges.subscribe(res => {
            this.updateSelection(res.start || null, res.end || null);
        });
    }

    updatePayslips() {
        this.api.payslip.getAllOfEmployee(this.employee_id()).subscribe({
            next: (res) => {
                this.allPayslips = res;
                this.updateSelection(null, null);
            }
        });
    }

    updateSelection(start: Date | null, end: Date | null) {
        if (end == null || start == null) {
            this.filteredPayslips = this.allPayslips;
        } else {
            this.filteredPayslips = this.allPayslips.filter(p => p.date >= start.getTime() && p.date <= end.getTime());
        }
        this.ploof.set(this.filteredPayslips);
        this.ref.detectChanges();
    }

    add() {
        const ref = this.dialog.open<DialogComponent, DialogDataType, boolean>(DialogComponent, {
            data: {
                component: PayslipFormComponent,
                title: "Création d'une paie",
                data: { payslip: null, employeeId: this.employee_id() },
                btnNotOk: ""
            }
        });
        ref.afterClosed().subscribe(() => {
            this.updatePayslips();
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

    download(p: Payslip) {
        this.api.payslip.pdf(p.id, (err) => {
            this.popup.openSnackBar({ message: err.message || "Échec du téléchargement" });
        });
    }
}
