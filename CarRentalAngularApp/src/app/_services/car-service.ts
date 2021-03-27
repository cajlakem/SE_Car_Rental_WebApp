import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Car} from "../_models/Car";

const API_URL = '/rest/';

@Injectable({
  providedIn: 'root',
})
export class CarService {

  constructor(private http: HttpClient) {
  }

  retrieveCars(): Observable<Array<Car>> {
    return this.http.get<Array<Car>>(API_URL + 'cars');
  }

}
