import {Component, OnInit, ViewChild} from '@angular/core';
import {TokenStorageService} from './_services/token-storage.service';
import {UserService} from './_services/user.service';
import {MatDialog} from '@angular/material/dialog';
import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {CarBookingListComponent} from "./car-booking-list/car-booking-list.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit {
  @ViewChild(CarBookingListComponent) carBookingList: CarBookingListComponent;
  // @ts-ignore
  static myapp;
  content?: string;
  title = 'C A R NOW';
  isLoggedIn = false;
  username?: string;
  currency: string;


  constructor(private userService: UserService, private tokenStorageService: TokenStorageService, public dialog: MatDialog) {
  }

  openLoginDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '300px',
    });
  }

  openRegisterDialog(): void {
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '300px',
    });
  }

  ngOnInit(): void {
    AppComponent.myapp = this;
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.username = user?.username;
    }

    this.userService.getPublicContent().subscribe(
      data => {
        console.log(data);
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  onCurrencySelectionEvent(event: any) {
    this.currency = event;
  }

  onCarBooked() {
    this.carBookingList.retrieveUsersBookings();
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
