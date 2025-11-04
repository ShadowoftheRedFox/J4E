import { inject, Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Subject } from 'rxjs';
import { Employee } from '../models/APIModels';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    api = inject(ApiService);

    isAuth = new Subject<boolean>();
    user: Employee | null = null;

    disconnect() {
        this.user = null;
        this.isAuth.next(false);
    }

    connect(employee: Employee) {
        this.user = employee;
        this.isAuth.next(true);
    }
}
