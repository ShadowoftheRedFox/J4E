import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/environment';

const ApiUrl = environment.API_URL + ":" + environment.API_PORT + "/";

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    readonly http = inject(HttpClient);

    readonly employee = {
        get(id: number) {
            // TODO api request
            return id;
        },
        // ...
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
