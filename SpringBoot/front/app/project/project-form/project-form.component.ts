import { Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ProjectStatus, Project } from '../../../models/APIModels';
import { toDateTime } from '../../../models/date';
import { ApiService } from '../../../services/api.service';
import { PopupService } from '../../../services/popup.service';
@Component({
    selector: 'app-project-form',
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
    templateUrl: './project-form.component.html',
    styleUrl: './project-form.component.scss'
})
export class ProjectFormComponent implements OnChanges {
    private readonly api = inject(ApiService);
    private readonly popup = inject(PopupService);

    readonly statuses = ProjectStatus;

    @Input() project: Project | null = null;
    @Output() out = new EventEmitter<number>();

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
        status: new FormControl<ProjectStatus>("ONGOING", [Validators.required]),
    });

    ngOnChanges(): void {
        this.formGroup.controls.name.setValue(this.project?.name || "");
        this.formGroup.controls.status.setValue(this.project?.status || "ONGOING");
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
        if (this.project == null) {
            this.api.project.create({
                id: 0,
                name: this.formGroup.value.name as string,
                status: this.formGroup.value.status as ProjectStatus,
                employees: []
            }).subscribe({
                next: (res) => {
                    if (res.status == 200) {
                        this.popup.openSnackBar({ message: "Projet créé" });
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
            this.api.project.update(this.project.id, {
                id: this.project.id,
                name: this.formGroup.value.name as string,
                status: this.formGroup.value.status as ProjectStatus,
                employees: []
            }).subscribe({
                next: () => {
                    this.popup.openSnackBar({ message: "Projet modifié" });
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
