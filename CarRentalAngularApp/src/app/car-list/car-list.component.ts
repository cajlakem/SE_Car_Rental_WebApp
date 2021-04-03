import {Component, Input, OnInit} from '@angular/core';
import {CarService} from '../_services/car-service';
import {Car} from '../_models/Car';
import {faCheckDouble, faTimesCircle} from '@fortawesome/free-solid-svg-icons';
import {AppComponent} from '../app.component';
import {UserService} from "../_services/user.service";
import {User} from "../_models/User";
import {Booking} from "../_models/Booking";


@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {
  @Input() currency: string;

  currentUser: User | null;
  cars: Car[] = [];
  userBookings: Booking[];
  iconDoubleCheck = faCheckDouble;
  iconCircleX = faTimesCircle;
  app: AppComponent;

  constructor(private userService: UserService, private carService: CarService) {
  }

  ngOnInit(): void {
    this.app = AppComponent.myapp;

    this.userService.getCurrentUser()?.subscribe((user: User) => {
        this.currentUser = user;
        this.retrieveUsersBookings();
      },
      err => {
        console.error(err.error.message);
      });

    this.retrieveAllCars();
  }

  retrieveUsersBookings() {
    if (this.currentUser) {
      this.carService.retrieveUserBookings(this.currentUser.id).subscribe(bookings => {
        this.userBookings = bookings;
      })
    }
  }

  retrieveAllCars() {
    this.carService.retrieveCars().subscribe(cars => {
        this.cars = cars;
      },
      err => {
        console.error(err.error.message);
      });
  }

  isCarFree(status: string): boolean {
    return status === 'FREE';
  }

  bookCar(car: Car) {
    if (this.currentUser) {
      this.carService.bookCar(car, this.currentUser, this.currency).subscribe((data: any) => {
          this.retrieveUsersBookings();
        },
        (err: any) => {
          console.error(err.error);
        });
    }
  }

}
