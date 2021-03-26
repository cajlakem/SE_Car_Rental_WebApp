import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'https://localhost:8443/api/v1/users/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const headers = { 'content-type': 'application/json', 'username' : username, 'password': password};
    return this.http.post(AUTH_API + 'login', {},  {headers: headers});
  }

  register(firstname: string, lastname: string, username: string, email: string, password: string, mobile: string): Observable<any> {
    const body = JSON.stringify({userName: username, password: password, firstName: firstname, lastName: lastname, email: email, mobile: mobile});
    const headers = { 'content-type': 'application/json', 'reenteredPassword' : password};
    return this.http.post(AUTH_API + 'register', body, {headers: headers});
  }
}
