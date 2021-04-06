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
    return this.http.get<User[]>(API_URL + '/users');
  }

  getCurrentUser(): Observable<User> | null {
    const userId = this.tokenService.getUser()?.id;
    if (userId) {
      return this.http.get<User>(API_URL + '/users/' + userId);
    }
    return null;
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', {responseType: 'text'});
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', {responseType: 'text'});
  }
}
