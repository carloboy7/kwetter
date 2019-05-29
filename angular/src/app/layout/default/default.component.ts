import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'app-default',
    templateUrl: './default.component.html',
    styleUrls: ['./default.component.css']
})
export class DefaultComponent implements OnInit {

    public search : string;
    constructor(private router : Router) {
    }

    ngOnInit() {
    }

    public makeSearch($event) : void{
        if($event.keyCode === 13){
            this.router.navigate(['search', this.search]);
        }
    }

    logout() {
        localStorage.removeItem("auth");
        this.router.navigate(['login']);
    }
}
