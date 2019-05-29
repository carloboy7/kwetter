import { Component, OnInit } from '@angular/core';
import {ApiService} from "../services/api.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  public kweets: Kweet[];
  public users : User[];
  constructor(private api : ApiService, private activeRoute: ActivatedRoute) { }

  ngOnInit() {
      this.activeRoute.params.subscribe(routeParams => {
          this.api.searchKweets(routeParams.query, x=>this.kweets=x);
          this.api.searchUsers(routeParams.query, x=>{this.users=x; console.log(x) });
      });

  }

}
