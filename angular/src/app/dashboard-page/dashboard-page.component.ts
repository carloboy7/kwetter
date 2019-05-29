import {Component, OnInit} from '@angular/core';
import {ApiService} from "../services/api.service";

@Component({
    selector: 'app-dashboard-page',
    templateUrl: './dashboard-page.component.html',
    styleUrls: ['./dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {
    private connection;
    public followState : number = 0;
    public showInput: boolean = true;
    public user: User;
    public following: User[];
    public followers: User[];
    public feed: Kweet[];
    public kweetText: string;

    constructor(protected api: ApiService) {
    }

    public followButtonClicked() : void{

    }
    ngOnInit() {
        this.api.getMe(x => this.user = x);
        this.api.getMeFollowing(x => {this.following = x; this.setConnection(x);});
        this.api.getMeFollowers(x => this.followers = x);
        this.setDashboard();
    }


    private setDashboard() {
        this.api.getMeDashboard(x => this.feed = x);
    }

    public sendNewKweet() {
        let kweet: Kweet = {
            id: 0,
            user: null,
            text: this.kweetText
        };
        console.log(kweet);
        this.api.postMeKweet(kweet, () => {
            this.setDashboard();
        }, () => {
        });
    }
    protected setConnection(list : User[] ) : void{
        this.connection = new WebSocket('ws://localhost/update/');
        this.connection.onmessage = (e) => {
            debugger;
            this.feed.push(JSON.parse(e.data));
        };

        this.connection.onerror = function (e) {
            console.log('error: ',  e);
        };
        this.connection.onopen = (e) =>{
            list.forEach(x => {
                const request = {
                    "queueName" : "kweet.new|"+x.id
                };
                console.log(request);
                this.connection.send( JSON.stringify(request) );
            });
        }
    }
}
