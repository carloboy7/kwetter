import { Component, OnInit } from '@angular/core';
import {LoginService} from "../services/login-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

    public userName : string;
    public password : string;
    public email : string;
    public images: string[];
    public selectedImage: number;
    constructor(private loginService : LoginService, private router: Router) { }

    ngOnInit() {
        this.images = [];
        this.selectedImage = 0;
        for(let i = 1; i <= 12; i++) {
            this.images.push("../../assets/src/avatars/"+i+".svg")
        }
    }

    public register() : boolean{
        this.loginService.register(this.userName, this.email, this.password, this.selectedImage+1, this.registerSuccess, this.registerFail);
        return false;
    }
    private registerSuccess = () : void =>{
        this.router.navigate(['dashboard']);
    };

    private registerFail() : void{
        alert("what you doing?");
    }

}
