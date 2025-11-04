import { Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Employee, EmployeeRole, EmployeePermission, Department } from '../../../models/APIModels';
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

    readonly roles = EmployeeRole;
    departments: Department[] = [];

    @Input() employee: Employee | null = null;
    @Output() out = new EventEmitter<number>();

    constructor() {
        this.api.department.getAll().subscribe(res => {
            if (res != null && Array.isArray(res)) {
                this.departments = res;
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

    formGroup = new FormGroup({
        username: new FormControl("None", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        firstName: new FormControl("None", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        lastName: new FormControl("None", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        password: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        role: new FormControl<EmployeeRole>("Administrateur", [Validators.required]),
        // permissions: new FormControl<EmployeePermission>("NONE", [Validators.required]), // TODO array input
        department: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
    });

    ngOnChanges(): void {
        this.formGroup.controls.username.setValue(this.employee?.username || "None");
        this.formGroup.controls.firstName.setValue(this.employee?.firstName || "None");
        this.formGroup.controls.lastName.setValue(this.employee?.lastName || "None");
        this.formGroup.controls.password.setValue(this.employee?.password || "");
        this.formGroup.controls.role.setValue(this.employee?.role || "Administrateur");
        this.formGroup.controls.department.setValue(this.employee?.department || 0);
        // this.formGroup.controls.permissions.setValue(this.employee?.permissions || "NONE"); // TODO
    }

    genericError(ctrl: FormControl) {
        if (ctrl.hasError("required")) {
            return "Champ requis";
        } else if (ctrl.hasError("minlength")) {
            return "La longueur minimale est " + (ctrl.getError("minlength").requiredLength)
        } else if (ctrl.hasError("maxlength")) {
            return "La lougueur maximale est " + (ctrl.getError("maxlength").requiredLength)
        }

        return "";
    }

    departmentError() {
        const ctrl = this.formGroup.controls.department;
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
                role: this.formGroup.value.role as EmployeeRole,
                permissions: [] as EmployeePermission[], // TODO
                department: this.formGroup.value.department as number,
                password: this.formGroup.value.password as string
            }).subscribe({
                next: () => {
                    this.popup.openSnackBar({ message: "Utilisateur créé" });
                    this.out.emit(1);
                },
                error: () => {
                    this.popup.openSnackBar({ message: "Échec de l'interaction" });
                    this.out.emit(0);
                }
            });
        } else {
            this.api.employee.update(this.employee.id, {
                id: this.employee.id,
                username: this.formGroup.value.username as string,
                firstName: this.formGroup.value.firstName as string,
                lastName: this.formGroup.value.lastName as string,
                role: this.formGroup.value.role as EmployeeRole,
                permissions: [] as EmployeePermission[], // TODO
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
}
