import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from "rxjs/index";
import {catchError} from "rxjs/internal/operators";
import {Router} from "@angular/router";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    public constructor(private router: Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            setHeaders: {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept'       : 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('auth')}`,
            },
        });

        let result = next.handle(req);
        return result;
    }
}