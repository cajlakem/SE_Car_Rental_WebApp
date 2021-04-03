import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const API_URL = '/rest';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  constructor(private http: HttpClient) {
  }

  retrieveCurrencyCodes(): Observable<string[]> {
    return this.http.get<string[]>(API_URL + '/currencyCodes')
  }
}
