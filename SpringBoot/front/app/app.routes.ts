import { Routes } from '@angular/router';
import { environment } from '../environments/environment';
import { MainComponent } from './main/main.component';
import { EmployeeComponent } from './employee/employee.component';
import { ProjectComponent } from './project/project.component';
import { DepartmentComponent } from './department/department.component';
import { PayslipComponent } from './payslip/payslip.component';
import { ReportComponent } from './report/report.component';
import { AuthComponent } from './auth/auth.component';
import { EmployeeFormComponent } from './employee/employee-form/employee-form.component';
import { DepartmentFormComponent } from './department/department-form/department-form.component';
import { ProjectFormComponent } from './project/project-form/project-form.component';

const FormatedTitle = " - " + environment.TITLE;

export const routes: Routes = [
    {
        title: environment.TITLE,
        path: "",
        component: MainComponent
    },
    {
        title: "Employés" + environment.TITLE,
        path: "employees",
        component: EmployeeComponent
    },
    {
        title: "Employés: Ajout" + environment.TITLE,
        path: "employees/add",
        component: EmployeeFormComponent
    },
    {
        title: "Projets" + environment.TITLE,
        path: "projects",
        component: ProjectComponent
    },
    {
        title: "Projets: Ajout" + environment.TITLE,
        path: "projects/add",
        component: ProjectFormComponent
    },
    {
        title: "Départements" + environment.TITLE,
        path: "departments",
        component: DepartmentComponent
    },
    {
        title: "Départements: Ajout" + environment.TITLE,
        path: "departments/add",
        component: DepartmentFormComponent
    },
    {
        title: "Paies" + environment.TITLE,
        path: "payslips",
        component: PayslipComponent
    },
    {
        title: "Rapport" + environment.TITLE,
        path: "report",
        component: ReportComponent
    },
    {
        title: "Authentification" + environment.TITLE,
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
