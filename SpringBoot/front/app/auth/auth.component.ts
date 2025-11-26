import { AfterViewInit, Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-auth',
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
    templateUrl: './auth.component.html',
    styleUrl: './auth.component.scss'
})
export class AuthComponent implements AfterViewInit {
    private readonly api = inject(ApiService);
    private readonly auth = inject(AuthService);
    private readonly router = inject(Router);

    private visible = false;
    visibility: "visibility" | "visibility_off" = "visibility_off";
    inputType: "text" | "password" = "password";

    formGroup = new FormGroup({
        username: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        password: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)])
    });

    ngAfterViewInit(): void {
        if (this.auth.user != null) {
            this.router.navigate([""]);
        }
    }

    visibilityToggle() {
        if (this.visible) {
            this.visibility = "visibility";
            this.inputType = "text";
        } else {
            this.visibility = "visibility_off";
            this.inputType = "password";
        }
        this.visible = !this.visible;
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

        this.api.auth.connect(this.formGroup.value.username || "", this.formGroup.value.password || "").subscribe({
            next: (user) => {
                this.auth.connect(user);
                this.router.navigate([""]);
            },
            error: (err) => {
                console.error(err);
                this.formGroup.setErrors({ failed: true });
            }
        });
    }
}
