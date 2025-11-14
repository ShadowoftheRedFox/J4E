import { Component, inject } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { MatButtonModule } from "@angular/material/button"

@Component({
    selector: 'app-main',
    imports: [
        MatButtonModule
    ],
    templateUrl: './main.component.html',
    styleUrl: './main.component.scss'
})
export class MainComponent {
    api = inject(ApiService);

    message = "";

    getMessage() {
        this.api.employee.getAll().subscribe(res => {
            this.message = JSON.stringify(res);
        })
    }
}
