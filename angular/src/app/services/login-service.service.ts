import {Injectable} from '@angular/core';
import {HttpBackend, HttpClient, HttpHeaders} from "@angular/common/http";
import * as BuildUrl from "build-url";
import {text} from "@angular/core/src/render3";

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    private http: HttpClient;

    constructor(handler: HttpBackend) {
        this.http = new HttpClient(handler);
    }

    public login(username: string, password: string, succes: () => void, error: () => void): void {

        const url: string = BuildUrl('/api/v1/auth/login', {
            queryParams: {
                username: username,
                password: password
            }
        });

        this.http.get(url, {responseType: 'text'}).subscribe((data: string) => {
            if (data === "wrong") {
                error();
            } else {
                localStorage.setItem("auth", data);
                succes();
            }
        });
    }

    public register(userName: string, email: string, password: string, number: number, registerSuccess: () => void, registerFail: () => void) {
        const headers = new HttpHeaders().set('Content-Type', 'application/json');

        this.http.post(
            '/api/v1/users',
            {
                "name": userName,
                "email": email,
                "password": password,
                "profileImageId": number
            },
            {headers, responseType: 'text'}
        ).subscribe((x) => {
            if (x === "true") {
                this.login(email, password, registerSuccess, registerFail);
            } else {
                registerFail();
            }
        }, x => {
            registerFail();
        }, () => {

        });
    }
}
