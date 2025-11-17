import { Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Department } from '../../../models/APIModels';
import { toDateTime } from '../../../models/date';
import { ApiService } from '../../../services/api.service';
import { PopupService } from '../../../services/popup.service';


@Component({
    selector: 'app-department-form',
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
    templateUrl: './department-form.component.html',
    styleUrl: './department-form.component.scss'
})
export class DepartmentFormComponent implements OnChanges {
    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);

    departments: Department[] = [];

    @Input() department: Department | null = null;
    @Output() out = new EventEmitter<number>();

    constructor() {
        this.api.department.getAll().subscribe(res => {
            if (res != null && Array.isArray(res)) {
                this.departments = res;
                // TODO check unique name
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
        name: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
    });

    ngOnChanges(): void {
        this.formGroup.controls.name.setValue(this.department?.name || "None");
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

    submitForm() {
        if (this.formGroup.invalid) return;
        if (this.department == null) {
            this.api.department.create({
                id: 0,
                name: this.formGroup.value.name as string,
                employees: []
            }).subscribe({
                next: (res) => {
                    if (res.status == 200) {
                        this.popup.openSnackBar({ message: "Département créé" });
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
            this.api.department.update({
                id: this.department.id,
                name: this.formGroup.value.name as string,
                employees: []
            }).subscribe({
                next: () => {
                    this.popup.openSnackBar({ message: "Departement modifié" });
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
