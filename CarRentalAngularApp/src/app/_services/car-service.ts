import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Car} from '../_models/Car';
import {User} from "../_models/User";
import {Booking} from "../_models/Booking";

const API_URL = '/rest/';

@Injectable({
  providedIn: 'root',
})
export class CarService {

  constructor(private http: HttpClient) {
  }

  retrieveCars(currencyCode: string): Observable<Array<Car>> {
    return this.http.get<Array<Car>>(API_URL + 'cars/findByState?state=FREE&currency=' + currencyCode);
  }

  retrieveUserBookings(userId: any): Observable<Array<Booking>> {
    return this.http.get<Array<Booking>>(API_URL + 'bookings/user/' + userId);
  }

  bookCar(carToBook: Car, user: User, currency: string): any {
    const bookingId = Number.parseInt(user.id.toString() + Date.now());

    const bookingRequest: Booking = {
      id: bookingId,
      remark: '',
      label: '',
      startTime: new Date(),
      endTime: new Date(),
      price: 0,
      car: carToBook,
      user: user,
      status: 'OPEN',
      currency: currency
    }
    return this.http.post(API_URL + 'booking', bookingRequest);
  }

  returnCar(booking: Booking) {
    return this.http.post(API_URL + 'booking/return', booking);
  }

}
