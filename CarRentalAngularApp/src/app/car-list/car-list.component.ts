import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CarService} from '../_services/car-service';
import {Car} from '../_models/Car';
import {faCheckDouble, faTimesCircle} from '@fortawesome/free-solid-svg-icons';
import {UserService} from '../_services/user.service';
import {User} from '../_models/User';
import {TokenStorageService} from '../_services/token-storage.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {
  @Input() currency: string;
  @Output() onCarBooked = new EventEmitter<any>();

  currentUser: User | null;
  cars: Car[] = [];
  iconDoubleCheck = faCheckDouble;
  iconCircleX = faTimesCircle;
  isLoggedIn = false;

  constructor(private userService: UserService, private carService: CarService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.userService.getCurrentUser()?.subscribe((user: User) => {
          this.currentUser = user;
        },
        err => {
          console.error(err.error.message);
        });
    }

    this.retrieveAllCars(this.currency);
  }

  retrieveAllCars(currency: string) {
    if (currency) {
      this.carService.retrieveCars(currency).subscribe(cars => {
          this.cars = cars;
        },
        err => {
          console.error(err.error.message);
        });
    }
  }

  isCarFree(status: string): boolean {
    return status === 'FREE';
  }

  bookCar(car: Car) {
    if (this.currentUser) {
      this.carService.bookCar(car, this.currentUser, this.currency).subscribe((data: any) => {
          this.retrieveAllCars(this.currency);
          this.onCarBooked.emit();
        },
        (err: any) => {
          console.error(err.error);
        });
    }
  }

}
