import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../_models/User';
import {TokenStorageService} from "./token-storage.service";

const API_URL = '/rest';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient, private tokenService: TokenStorageService) {
  }

  getPublicContent(): Observable<User[]> {
    return this.http.get<User[]>('/rest/userauthorizationtbackend/users');
  }

  getCurrentUser(): Observable<User> | null {
    const userId = this.tokenService.getUser()?.username;
    if (userId) {
      return this.http.get<User>('/rest/userauthorizationtbackend/users/' + userId);
    }
    return null;
  }
}
