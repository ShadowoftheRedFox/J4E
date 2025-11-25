import { Component, inject } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Department, Employee, EmployeeRank, Project } from '../../models/APIModels';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-report',
    imports: [
        MatButtonModule,
        MatIconModule
    ],
    templateUrl: './report.component.html',
    styleUrl: './report.component.scss'
})
export class ReportComponent {
    private readonly api = inject(ApiService);
    private readonly auth = inject(AuthService);

    departments: Department[] = [];
    employees: Employee[] = [];
    projects: Project[] = [];
    readonly ranks = EmployeeRank;

    constructor() {
        this.api.department.getAll().subscribe({
            next: (res) => {
                this.departments = res;
            }
        });

        this.api.employee.getAll().subscribe({
            next: (res) => {
                this.employees = res;
            }
        });

        this.api.project.getAll().subscribe({
            next: (res) => {
                this.projects = res;
            }
        });
    }

    getProjectMeanEmployee() {
        let sum = 0;
        this.projects.forEach(p => {
            sum += p.employees.length;
        });
        return sum / this.projects.length;
    }

    getDepartmentMeanEmployee() {
        let sum = 0;
        this.departments.forEach(d => {
            sum += d.employees.length;
        });
        return sum / this.departments.length;
    }

    getProjectOngoing() {
        return this.projects.filter(p => p.status === "ONGOING").length;
    }

    getProjectCanceled() {
        return this.projects.filter(p => p.status === "CANCELED").length;
    }

    getProjecFinished() {
        return this.projects.filter(p => p.status === "FINISHED").length;
    }

    getEmployeeOfRank(rank: EmployeeRank) {
        return this.employees.filter(e => e.ranks.includes(rank)).length;
    }
}
