import { ChangeDetectionStrategy, Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { toDateTime } from '../../../models/date';
import { ApiService } from '../../../services/api.service';
import { PopupService } from '../../../services/popup.service';
import { Employee, EmployeeRank, Payslip } from '../../../models/APIModels';
import { MatDatepickerModule } from "@angular/material/datepicker"
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
    selector: 'app-payslip-form',
    imports: [
        MatIconModule,
        MatButtonModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        ReactiveFormsModule,
        MatCheckboxModule,
        MatTooltipModule,
        MatDatepickerModule,
    ],
    templateUrl: './payslip-form.component.html',
    styleUrl: './payslip-form.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [provideNativeDateAdapter()],
})
export class PayslipFormComponent implements OnChanges {
    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);
    readonly ranks = EmployeeRank;

    @Input() payslip: Payslip | null = null;
    @Input({ required: true }) employeeId!: number;
    @Output() out = new EventEmitter<number>();

    employee: Employee | null = null;

    formGroup: FormGroup<{
        hour: FormControl<number | null>;
        wage: FormControl<number | null>;
        bonus: FormControl<number | null>;
        malus: FormControl<number | null>;
        date: FormControl<Date | null>;
    }>;

    constructor() {
        this.formGroup = new FormGroup({
            hour: new FormControl<number>(0, [Validators.required, Validators.min(0)]),
            wage: new FormControl<number>(0, [Validators.required, Validators.min(0)]),
            bonus: new FormControl<number>(0, [Validators.required, Validators.min(0)]),
            malus: new FormControl<number>(0, [Validators.required, Validators.min(0)]),
            date: new FormControl<Date>(new Date(), [Validators.required]),
        });
    }

    ngOnChanges(): void {
        if (this.payslip != null) {
            this.api.employee.get(this.payslip.employee).subscribe(res => {
                this.employee = res;
            });

            this.formGroup.controls["hour"].setValue(this.payslip.hour);
            this.formGroup.controls["wage"].setValue(this.payslip.wage);
            this.formGroup.controls["bonus"].setValue(this.payslip.bonus);
            this.formGroup.controls["malus"].setValue(this.payslip.malus);
            this.formGroup.controls["date"].setValue(new Date(this.payslip.date));

            this.formGroup.markAllAsTouched();
        }
    }

    format(date: string) {
        return toDateTime(date);
    }

    genericError(ctrl: AbstractControl) {
        if (ctrl.hasError("required")) {
            return "Champ requis";
        } else if (ctrl.hasError("minlength")) {
            return "La longueur minimale est " + (ctrl.getError("minlength").requiredLength);
        } else if (ctrl.hasError("maxlength")) {
            return "La longueur maximale est " + (ctrl.getError("maxlength").requiredLength);
        } else if (ctrl.hasError("taken")) {
            return "Nom d'utilisateur déjà existant";
        } else if (ctrl.hasError("min")) {
            return "La valeur minimale est " + (ctrl.getError("min").min);
        } else if (ctrl.hasError("max")) {
            return "La valeur maximale est " + (ctrl.getError("max").max);
        }

        return "";
    }

    submitForm() {
        if (this.formGroup.invalid) return;
        if (this.payslip == null) {
            this.api.payslip.create({
                id: 0,
                employee: this.employeeId,
                hour: this.formGroup.value.hour as number,
                wage: this.formGroup.value.wage as number,
                bonus: this.formGroup.value.bonus as number,
                malus: this.formGroup.value.malus as number,
                date: this.formGroup.value.date?.getTime(),
            }).subscribe({
                next: (res) => {
                    if (res.status == 200) {
                        this.popup.openSnackBar({ message: "Feuille créée" });
                        this.out.emit(1);
                    } else {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                        this.out.emit(0);
                    }
                }, error: () => {
                    this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    this.out.emit(0);
                }
            });
        } else {
            this.api.payslip.update({
                id: this.payslip.id,
                employee: this.payslip.employee,
                hour: this.formGroup.value.hour as number,
                wage: this.formGroup.value.wage as number,
                bonus: this.formGroup.value.bonus as number,
                malus: this.formGroup.value.malus as number,
                date: this.formGroup.value.date?.getTime() as number,
            }).subscribe({
                next: (res) => {
                    if (res.status == 200) {
                        this.popup.openSnackBar({ message: "Feuille modifiée" });
                        this.out.emit(1);
                    } else {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                        this.out.emit(0);
                    }
                }, error: () => {
                    this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    this.out.emit(0);
                }
            });
        }
    }
}
