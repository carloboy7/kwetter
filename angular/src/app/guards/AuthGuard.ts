import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthGuard implements CanActivate {
    public constructor(private router: Router) {}
    canActivate() {
        if(!localStorage.getItem('auth')){
            this.router.navigate(['login']);
            return false;
        }
        return true;
    }
}