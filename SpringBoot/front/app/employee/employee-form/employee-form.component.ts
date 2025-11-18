import { Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Employee, EmployeeRank, EmployeePermission, Department } from '../../../models/APIModels';
import { toDateTime } from '../../../models/date';
import { ApiService } from '../../../services/api.service';
import { PopupService } from '../../../services/popup.service';

@Component({
    selector: 'app-employee-form',
    imports: [
        MatIconModule,
        MatButtonModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        ReactiveFormsModule,
        MatCheckboxModule,
        MatTooltipModule
    ],
    templateUrl: './employee-form.component.html',
    styleUrl: './employee-form.component.scss'
})
export class EmployeeFormComponent implements OnChanges {
    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);

    readonly ranks = EmployeeRank;
    readonly permissions = EmployeePermission;
    departments: Department[] = [];
    allEmployees: Employee[] = [];

    @Input() employee: Employee | null = null;
    @Output() out = new EventEmitter<number>();

    constructor() {
        this.api.department.getAll().subscribe(res => {
            if (res != null && Array.isArray(res)) {
                this.departments = res;
            }
        });
        this.api.employee.getAll().subscribe(res => {
            if (res != null && Array.isArray(res)) {
                this.allEmployees = res;
            }
        });

        this.initForm();

        this.formGroup.controls["username"].valueChanges.subscribe(res => {
            if (res == null || res.length == 0) {
                return;
            }
            if (this.allEmployees.find(e => e.username == res) != undefined) {
                this.formGroup.controls["username"].setErrors({ taken: true });
            }
        });
    }

    format(date: string) {
        return toDateTime(date);
    }

    getAge(date?: string) {
        const now = new Date();
        const then = new Date(date as string);
        return now.getFullYear() - then.getFullYear();
    }

    formGroup!: FormGroup;
    private readonly fb = new FormBuilder();

    initForm(): void {
        this.formGroup = this.fb.group({
            username: this.fb.control("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            firstName: this.fb.control("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            lastName: this.fb.control("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            password: this.fb.control("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            ranks: this.fb.array<EmployeeRank>([], [Validators.required, Validators.minLength(1)]),
            permissions: this.fb.array<EmployeePermission>([]),
            department: this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
        });
    }

    ngOnChanges(): void {
        if (this.employee != null) {
            this.formGroup.controls["username"].setValue(this.employee.username || "");
            this.formGroup.controls["firstName"].setValue(this.employee.firstName || "");
            this.formGroup.controls["lastName"].setValue(this.employee.lastName || "");
            this.formGroup.controls["password"].setValue(this.employee.password || "");
            this.formGroup.controls["department"].setValue(this.employee.department || 0);

            this.employee.ranks.forEach(r => {
                this.changeRank(r, true);
            });
            this.employee.permissions.forEach(p => {
                this.changePermission(p, true);
            });

            this.formGroup.controls["password"].removeValidators([Validators.required, Validators.minLength(3)]);

            if (this.employee.id === 1) {
                this.formGroup.controls["username"].disable();
                this.formGroup.controls["firstName"].disable();
                this.formGroup.controls["lastName"].disable();
                this.permissionsCtrl.disable();
            }
        }
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
        }

        return "";
    }

    departmentError() {
        const ctrl = this.formGroup.controls["department"];
        if (ctrl.hasError("required") || ctrl.hasError("min")) {
            return "Champ requis";
        }

        return "";
    }

    submitForm() {
        if (this.formGroup.invalid) return;
        if (this.employee == null) {
            this.api.employee.create({
                id: 0,
                username: this.formGroup.value.username as string,
                firstName: this.formGroup.value.firstName as string,
                lastName: this.formGroup.value.lastName as string,
                ranks: this.ranksCtrl.value,
                permissions: this.permissionsCtrl.value,
                department: this.formGroup.value.department as number,
                password: this.formGroup.value.password as string
            }).subscribe({
                next: (res) => {
                    if (res.status == 200) {
                        this.popup.openSnackBar({ message: "Utilisateur créé" });
                        this.out.emit(1);
                    } else {
                        this.popup.openSnackBar({ message: "Échec de l'interaction" });
                        this.out.emit(0);
                    }
                },
                error: () => {
                    this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    this.out.emit(0);
                }
            });
        } else {
            this.api.employee.update({
                id: this.employee.id,
                username: this.formGroup.value.username || this.employee.username as string,
                firstName: this.formGroup.value.firstName || this.employee.firstName as string,
                lastName: this.formGroup.value.lastName || this.employee.lastName as string,
                ranks: this.ranksCtrl.value,
                permissions: this.permissionsCtrl.value,
                department: this.formGroup.value.department as number,
                password: this.formGroup.value.password as string
            }).subscribe({
                next: () => {
                    this.popup.openSnackBar({ message: "Utilisateur modifié" });
                    this.out.emit(1);
                },
                error: () => {
                    this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    this.out.emit(0);
                }
            });
        }
    }

    get ranksCtrl(): FormArray<FormControl<EmployeeRank>> {
        return this.formGroup.controls["ranks"] as FormArray;
    }

    get permissionsCtrl(): FormArray<FormControl<EmployeePermission>> {
        return this.formGroup.controls["permissions"] as FormArray;
    }

    changeRank(rank: EmployeeRank, checked: boolean) {
        let i = -1;
        this.ranksCtrl.controls.find((v, id) => {
            if (v.value === rank) {
                i = id;
                return true;
            }
            return false;
        });

        if (!checked) {
            this.ranksCtrl.removeAt(i);
        } else {
            this.ranksCtrl.insert(this.ranksCtrl.length, this.fb.nonNullable.control(rank));
        }
    }

    changePermission(permisson: EmployeePermission, checked: boolean) {
        let i = -1;
        this.permissionsCtrl.controls.find((v, id) => {
            if (v.value === permisson) {
                i = id;
                return true;
            }
            return false;
        });

        if (!checked) {
            this.permissionsCtrl.removeAt(i);
        } else {
            this.permissionsCtrl.insert(this.ranksCtrl.length, this.fb.nonNullable.control(permisson));
        }
    }
}
