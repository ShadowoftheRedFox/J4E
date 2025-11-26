import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-header',
    imports: [
        RouterLink,
        MatButtonModule,
        MatIconModule
    ],
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
})
export class HeaderComponent {
    readonly auth = inject(AuthService);

    isAuth = false;

    constructor() {
        this.auth.isAuth.subscribe(res => {
            this.isAuth = res;
        });
    }

    disconnect() {
        this.auth.disconnect();
    }

    getFirstLastNames(): string {
        return this.auth.user?.firstName + " " + this.auth.user?.lastName;
    }
}
