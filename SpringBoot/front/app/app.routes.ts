import { Routes } from '@angular/router';
import { environment } from '../environments/environment';
import { MainComponent } from './main/main.component';
import { EmployeeComponent } from './employee/employee.component';
import { ProjectComponent } from './project/project.component';
import { DepartmentComponent } from './department/department.component';
import { PayslipComponent } from './payslip/payslip.component';
import { ReportComponent } from './report/report.component';
import { AuthComponent } from './auth/auth.component';
import { VisualizationComponent } from './payslip/visualization/visualization.component';

const FormatedTitle = " - " + environment.TITLE;

export const routes: Routes = [
    {
        title: environment.TITLE,
        path: "",
        component: MainComponent
    },
    {
        title: "Employés" + FormatedTitle,
        path: "employees",
        component: EmployeeComponent
    },
    {
        title: "Projets" + FormatedTitle,
        path: "projects",
        component: ProjectComponent
    },
    {
        title: "Départements" + FormatedTitle,
        path: "departments",
        component: DepartmentComponent
    },
    {
        title: "Paies" + FormatedTitle,
        path: "payslips",
        component: PayslipComponent
    },
    {
        title: "Paies: visualisation" + FormatedTitle,
        path: "payslips/:id",
        component: VisualizationComponent
    },
    {
        title: "Rapport" + FormatedTitle,
        path: "report",
        component: ReportComponent
    },
    {
        title: "Authentification" + FormatedTitle,
        path: "auth",
        component: AuthComponent
    },
    {
        // Error 404, redirect to main
        title: "Erreur 404" + FormatedTitle,
        path: "**",
        redirectTo: ""
    }
];
