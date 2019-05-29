import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { DashboardPageComponent } from './dashboard-page/dashboard-page.component';
import { DefaultComponent } from './layout/default/default.component';
import { EntryComponent } from './dashboardPage/layout/entry/entry.component';
import { KweetComponent } from './dashboardPage/layout/kweet/kweet.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./interceptor/auth.interceptor";
import {AuthGuard} from "./guards/AuthGuard";
import { SearchPageComponent } from './search-page/search-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'search/:query', component: SearchPageComponent },
  { path: 'user/:id', component: UserPageComponent },
  { path: 'dashboard', component: DashboardPageComponent, canActivate: [AuthGuard], },
];


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    DashboardPageComponent,
    DefaultComponent,
    EntryComponent,
    KweetComponent,
    SearchPageComponent,
    UserPageComponent,
    RegisterPageComponent,
  ],
  imports: [
      HttpClientModule,
    FormsModule,
    BrowserModule,
    RouterModule.forRoot(
      appRoutes,
    )
  ],
  providers: [
      AuthGuard,
      {
          provide : HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi   : true,
      },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
