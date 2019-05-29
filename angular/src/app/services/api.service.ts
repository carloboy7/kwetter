import {Injectable} from '@angular/core';
import {HttpBackend, HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import * as BuildUrl from "build-url";

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    private unAuthClient: HttpClient;

    constructor(private http: HttpClient, private router: Router, handler: HttpBackend) {
        this.unAuthClient = new HttpClient(handler);
    }

    private fail = (err: any) => { //<--if error use a catch
        if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
                localStorage.removeItem("auth");
                this.router.navigate(['login']);
            }
        }
    };

    public getMe(result: (User) => void): void {
        //http://localhost/api/v1/me
        this.http.get('/api/v1/me').subscribe(result, this.fail);

    }

    public getMeFollowing(result: (users: User[]) => void): void {
        this.http.get('/api/v1/me/following').subscribe(result, this.fail);
    }

    public getMeFollowers(result: (users: User[]) => void): void {
        //http://localhost/api/v1/me/followers
        this.http.get('/api/v1/me/followers').subscribe(result, this.fail);
    }

    public getMeDashboard(result: (kweets: Kweet[]) => void): void {
        this.http.get('/api/v1/me/dashboard').subscribe(result, this.fail);
    }

    public getUser(id : number, result: (User) => void): void {
        //http://localhost/api/v1/me
        this.unAuthClient.get('/api/v1/users/'+id).subscribe(result, this.fail);
    }

    public getUserFollowing(id : number, result: (users: User[]) => void): void {
        this.unAuthClient.get('/api/v1/users/'+id+'/following').subscribe(result, this.fail);
    }

    public getUserKweets(id : number, result: (kweets: Kweet[]) => void): void {
        this.unAuthClient.get('/api/v1/users/'+id+'/kweets').subscribe(result, this.fail);
    }
    public getUserFollowers(id : number, result: (users: User[]) => void): void {
        //http://localhost/api/v1/me/followers
        this.unAuthClient.get('/api/v1/users/'+id+'/followers').subscribe(result, this.fail);
    }

    public postMeFollowUser(userId: number, success: () => void, fail: () => void): void {
        this.http.post("/api/v1/me/following/"+userId, "").subscribe(success, x => {
            this.fail(x);
            fail();
        }, () => {
        });
    }

    public unfollowUser(userId: number, success: () => void, fail: () => void): void {
        this.http.delete("/api/v1/me/following/"+userId).subscribe(success, x => {
            this.fail(x);
            fail();
        }, () => {
        });
    }

    public postMeKweet(kweet: Kweet, success: () => void, fail: () => void): void {
        console.log("Post");
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        this.http.post("/api/v1/me/kweet", JSON.stringify(kweet), httpOptions,).subscribe(success, x => {
            this.fail(x);
            fail();
        }, () => {
        });

    }

    public searchKweets(query: string, success: (kweets: Kweet[]) => void): void {
        ///Kwetter/v1/kweet/search
        const url: string = BuildUrl('/api/v1/kweet/search', {
            queryParams: {
                query: query,
            }
        });

        this.unAuthClient.get(url).subscribe((data: Kweet[]) => {
            success(data);
        });
    }

    public searchUsers(query: string, success: (kweets: User[]) => void): void {
        ///Kwetter/v1/kweet/search
        const url: string = BuildUrl('/api/v1/users/search', {
            queryParams: {
                query: query,
            }
        });

        this.unAuthClient.get(url).subscribe((data: User[]) => {
            success(data);
        });
    }

}
