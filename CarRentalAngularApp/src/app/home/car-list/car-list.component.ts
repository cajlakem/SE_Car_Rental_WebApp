import { Component, OnInit } from '@angular/core';
import {CarService} from "../../_services/car-service";

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.carService.retrieveCars().subscribe(cars => {
      console.log(cars);
    });

  }
}
