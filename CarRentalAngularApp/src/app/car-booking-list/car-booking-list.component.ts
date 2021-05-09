import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from "../_models/User";
import {UserService} from "../_services/user.service";
import {CarService} from "../_services/car-service";
import {Booking} from "../_models/Booking";
import {faCheckDouble, faTimesCircle} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-car-booking-list',
  templateUrl: './car-booking-list.component.html',
  styleUrls: ['./car-booking-list.component.css']
})
export class CarBookingListComponent implements OnInit {
  @Output() onCarReturned = new EventEmitter<any>();

  currentUser: User | null;
  userBookings: Booking[];

  iconDoubleCheck = faCheckDouble;
  iconCircleX = faTimesCircle;

  constructor(private userService: UserService, private carService: CarService) {
  }

  ngOnInit(): void {
    this.userService.getCurrentUser()?.subscribe((user: User) => {
        this.currentUser = user;
        this.retrieveUsersBookings();
      },
      err => {
        console.error(err.error.message);
      });

  }

  retrieveUsersBookings() {
    if (this.currentUser) {
      this.carService.retrieveUserBookings(this.currentUser.id).subscribe(bookings => {
        this.userBookings = bookings;
      });
    }
  }

  isCarFree(status: string): boolean {
    return status === 'FREE';
  }

  hasBookings() {
    return this.userBookings?.length > 0;
  }

  isBookingOrderBooked(status: string): boolean {
    return status === 'BOOKED';
  }

  returnCar(booking: Booking) {
    booking.endTime = new Date();
    this.carService.returnCar(booking).subscribe((booked) => {
      this.retrieveUsersBookings();
      this.onCarReturned.emit();
    }, error => console.error(error));
  }
}
