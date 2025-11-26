import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Employee, EmployeePermission } from '../models/APIModels';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
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

    hasPerm(perm: EmployeePermission | EmployeePermission[]) {
        if (!this.isAuth || this.user == null) { return false; }
        if (Array.isArray(perm)) {
            let all = true;
            perm.forEach(p => {
                all = all && this.user!.permissions.includes(p);
            });
            return all;
        } else {
            return this.user.permissions.includes(perm);
        }
    }
}
