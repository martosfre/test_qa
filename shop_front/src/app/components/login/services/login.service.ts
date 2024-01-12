import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isLoggedIn = false;
  nextRoute?: Route;
  role?:string;

  constructor(private router: Router, private httpClient: HttpClient) { }

  isAuthenticated() {
    return localStorage.getItem('loggedIn') === '1';
  }

  getCurrentRole(){
    return localStorage.getItem('role');
  }

  login(userName: string, password: string): Promise<any> {
    var headers = {
      authorization: "Basic " + btoa(userName + ":" + password)};

    return this.httpClient.post("/api/v1/login/user", {userName: userName}, {headers : headers}).toPromise();
  }

  logout() {
    localStorage.setItem('loggedIn', '0');
    localStorage.setItem('role', '');
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }

  getAllClients(): Observable<any> {
    return this.httpClient.get("/api/v1/client");
  }

}
