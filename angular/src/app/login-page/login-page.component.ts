import { Component, OnInit } from '@angular/core';
import {LoginService} from "../services/login-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  public userName : string;
  public password : string;
  constructor(private loginService : LoginService, private router: Router) { }

  ngOnInit() {
  }

  public login() : boolean{
    this.loginService.login(this.userName, this.password, this.loginSuccess, this.loginError);
    return false;
  }

  private loginSuccess = () : void => {
      this.router.navigate(['/dashboard'])

  };
  private loginError = (): void =>{
    alert("Login incorrect");
  }
}
