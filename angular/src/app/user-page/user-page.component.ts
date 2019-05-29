import {Component, OnInit} from '@angular/core';
import {DashboardPageComponent} from "../dashboard-page/dashboard-page.component";
import {ApiService} from "../services/api.service";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-user-page',
    templateUrl: '../dashboard-page/dashboard-page.component.html',
    styleUrls: ['../dashboard-page/dashboard-page.component.css']
})
export class UserPageComponent extends DashboardPageComponent{

    constructor(api : ApiService, private activeRoute : ActivatedRoute) {
        super(api);
        this.showInput = false;
    }
    public followButtonClicked () : void{
        if(this.followState === 2){
            this.followState = 3;
            this.api.postMeFollowUser(this.user.id, () =>{
            }, () => {});
        } else {
            this.followState = 2;
            this.api.unfollowUser(this.user.id, () =>{
            }, () => {});
        }
    }

    ngOnInit() {


        this.activeRoute.params.subscribe(routeParams => {
            this.api.getUser(routeParams.id,x => {
                this.user = x;
                this.checkIfFollowing();
                this.setConnection([x]);
            });

            this.api.getUserFollowing(routeParams.id, x => this.following = x);
            this.api.getUserFollowers(routeParams.id, x => this.followers = x);
            this.api.getUserKweets(routeParams.id,x => this.feed = x);
        });
    }
    private checkIfFollowing() : void{
        this.api.getMeFollowing(x=>this.followState = x.filter((user, index, t) => user.id == this.user.id).length > 0 ? 3 : 2);
    }


}
