import { Routes } from '@angular/router';
import { environment } from '../environments/environment';
import { MainComponent } from './main/main.component';

const FormatedTitle = " - " + environment.TITLE;

export const routes: Routes = [
    {
        title: environment.TITLE,
        path: "",
        component: MainComponent
    },
    {
        // Error 404, redirect to main
        title: "Erreur 404" + FormatedTitle,
        path: "**",
        redirectTo: ""
    }
];
