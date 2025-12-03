import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { BaseResponse, Department, Employee, Payslip, Project } from '../models/APIModels';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

const ApiUrl = environment.API_URL + ":" + environment.API_PORT + "/";

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    readonly http = inject(HttpClient);
    readonly authService = inject(AuthService);

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
            return this.sendApiRequest<BaseResponse>("POST", "employee", employee, "Creating new employee");
        },
        update: (employee: Employee) => {
            return this.sendApiRequest<BaseResponse>("PUT", "employee/" + employee.id, employee, "Updating employee " + employee.id);
        },
        delete: (id: number) => {
            return this.sendApiRequest<BaseResponse>("DELETE", "employee/" + id, {}, "Deleting employee" + id);
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
            return this.sendApiRequest<BaseResponse>("POST", "department", department, "Creating new department");
        },
        update: (department: Department) => {
            return this.sendApiRequest<BaseResponse>("PUT", "department/" + department.id, department, "Updating department " + department.id);
        },
        delete: (id: number) => {
            return this.sendApiRequest<BaseResponse>("DELETE", "department/" + id, {}, "Deleting department " + id);
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
            return this.sendApiRequest<BaseResponse>("POST", "project", project, "Creating new project");
        },
        update: (project: Project) => {
            return this.sendApiRequest<BaseResponse>("PUT", "project/" + project.id, project, "Updating project " + project.id);
        },
        delete: (id: number) => {
            return this.sendApiRequest<BaseResponse>("DELETE", "project/" + id, {}, "Deleting project " + id);
        },
    }

    readonly payslip = {
        getAll: () => {
            return this.sendApiRequest<Payslip[]>("GET", "payslip", {}, "Getting all payslips");
        },
        getAllOfEmployee: (employee_id: number) => {
            return this.sendApiRequest<Payslip[]>("GET", "payslip/employee/" + employee_id, {}, "Getting payslips of employee " + employee_id);
        },
        get: (id: number) => {
            return this.sendApiRequest<Payslip>("GET", "payslip/" + id, {}, "Getting payslip " + id);
        },
        update: (payslip: Payslip) => {
            return this.sendApiRequest<BaseResponse>("PUT", "payslip/" + payslip.id, payslip, "Updating payslip " + payslip.id);
        },
        create: (payslip: Partial<Payslip>) => {
            return this.sendApiRequest<BaseResponse>("POST", "payslip", payslip, "Creating new payslip");
        },
        delete: (id: number) => {
            return this.sendApiRequest<BaseResponse>("DELETE", "payslip/" + id, {}, "Deleting payslip " + id);
        },
        raw_pdf: (id: number) => {
            return this.http.get(ApiUrl + "payslip/" + id + "/pdf?session_id="+(this.authService.user?.id || 0), {
                responseType: 'arraybuffer',
                headers: new HttpHeaders().append('Content-Type', 'application/pdf'),
            });
        },
        pdf: (id: number, err_callback?: (err: BaseResponse) => void) => {
            this.payslip.raw_pdf(id).subscribe({
                next: (r) => {
                    try {
                        const file = new Blob([r], { type: "application/pdf" });
                        const url: string = window.URL.createObjectURL(file);
                        const anchorElement: HTMLAnchorElement = document.createElement('a');
                        anchorElement.download = "payslip" + id;
                        anchorElement.href = url;
                        anchorElement.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));
                        // eslint-disable-next-line @typescript-eslint/no-unused-vars
                    } catch (_) {
                        if (err_callback != undefined) {
                            err_callback({
                                error: "Can't load PDF",
                                status: 400
                            });
                        }
                    }
                },
                error: (err) => {
                    console.error(err);
                    if (err_callback != undefined) {
                        err_callback({
                            error: "Can't load PDF",
                            status: 400
                        });
                    }
                }
            });
        }
    }

    private sendApiRequest<T>(
        method: "GET" | "POST" | "PUT" | "DELETE" | "PATCH",
        endpoint: string,
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        parameters: any = {},
        message?: string,
    ): Observable<T> {
        // add the connected user as "session_id"
        parameters["session_id"] = this.authService.user?.id || 0;

        let urlParameters = "";
        if (parameters != undefined && Object.keys(parameters).length > 0) {
            urlParameters = "?" + Object.entries(parameters).flatMap(v => v[0] + "=" + v[1]).join("&");
        }

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
                return this.http.delete<T>(ApiUrl + endpoint + urlParameters, parameters) as Observable<T>;
        }
    }
}
