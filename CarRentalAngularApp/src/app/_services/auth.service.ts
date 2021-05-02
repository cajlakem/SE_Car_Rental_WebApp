import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthUser} from "../_models/AuthUser";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<AuthUser> {
    const headers = {'content-type': 'application/json', 'username': username, 'password': password};
    return this.http.post<AuthUser>('/rest/userauthorizationtbackend/users/login', {}, {headers: headers});
  }

  register(firstname: string, lastname: string, username: string, email: string, password: string, mobile: string): Observable<any> {
    const body = JSON.stringify({
      userName: username,
      password: password,
      firstName: firstname,
      lastName: lastname,
      email: email,
      mobile: mobile
    });
    const headers = {'content-type': 'application/json', 'reenteredPassword': password};
    return this.http.post('/rest/signupmanagementbackend/users/register', body, {headers: headers});
  }
}
