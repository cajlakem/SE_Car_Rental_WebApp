import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {

  bookings = [];
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.sendGetRequest().subscribe((data) => {
      console.log(data);
      // @ts-ignore
      this.bookings = data;
    });
  }

}

export class Booking {
  constructor(
    public id: number,
    public label: string,
    public car: string,
    public price: string,
    public fromDate: string,
    public toDate: string
  ) {
  }
}
