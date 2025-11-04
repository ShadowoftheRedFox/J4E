import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Department, Employee, Project } from '../models/APIModels';

const ApiUrl = environment.API_URL + ":" + environment.API_PORT + "/";

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    readonly http = inject(HttpClient);

    readonly auth = {
        connect: (username: string, password: string) => {
            return this.sendApiRequest<Employee>("POST", "auth", { username: username, password: password }, "Connecting with " + username);
        },
    }

    readonly employee = {
        get: (id: number) => {
            return this.sendApiRequest<Employee>("GET", "employee/" + id, {}, "Getting employee " + id);
        },
        getAll: () => {
            return this.sendApiRequest<Employee[]>("GET", "employee", {}, "Getting all employees");
        },
        create: (employee: Employee) => {
            return this.sendApiRequest<boolean>("POST", "employee/add", employee, "Creating new employee");
        },
        update: (id: number, employee: Employee) => {
            return this.sendApiRequest<boolean>("PUT", "employee/" + id, employee, "Updating employee " + id);
        },
        delete: (id: number) => {
            return this.sendApiRequest<boolean>("DELETE", "employee/" + id, {}, "Deleting employee" + id);
        }
    }

    readonly department = {
        get: (id: number) => {
            return this.sendApiRequest<Department>("GET", "department/" + id, {}, "Getting department " + id);
        },
        getAll: () => {
            return this.sendApiRequest<Department[]>("GET", "department", {}, "Getting all departments");
        },
        create: (department: Department) => {
            return this.sendApiRequest<boolean>("POST", "department/add", department, "Creating new department");
        },
        update: (id: number, department: Department) => {
            return this.sendApiRequest<boolean>("PUT", "department/" + id + "/edit", department, "Updating department " + id);
        },
    }

    readonly project = {
        get: (id: number) => {
            return this.sendApiRequest<Project>("GET", "project/" + id, {}, "Getting project " + id);
        },
        getAll: () => {
            return this.sendApiRequest<Project[]>("GET", "project", {}, "Getting all projects");
        },
        create: (project: Project) => {
            return this.sendApiRequest<boolean>("POST", "project/add", project, "Creating new project");
        },
        update: (id: number, project: Project) => {
            return this.sendApiRequest<boolean>("PUT", "project/" + id + "/edit", project, "Updating project " + id);
        },
    }

    private sendApiRequest<T>(
        method: "GET" | "POST" | "PUT" | "DELETE" | "PATCH",
        endpoint: string,
        parameters: object = {},
        message?: string,
    ) {
        const urlParameters = parameters != undefined && Object.keys(parameters).length > 0
            ? "?data=" + JSON.stringify(parameters)
            : "";

        if (message !== undefined && message != null && message.length > 0) {
            console.info("[API] " + message);
        }

        switch (method) {
            case "GET":
                return this.http.get<T>(ApiUrl + endpoint + urlParameters);
            case "POST":
                return this.http.post<T>(ApiUrl + endpoint, parameters);
            case "PUT":
                return this.http.put<T>(ApiUrl + endpoint, parameters);
            case "PATCH":
                return this.http.patch<T>(ApiUrl + endpoint, parameters);
            case "DELETE":
                return this.http.delete<T>(ApiUrl + endpoint, parameters);
        }
    }
}
