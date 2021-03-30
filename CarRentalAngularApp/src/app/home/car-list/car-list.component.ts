import { Component, OnInit } from '@angular/core';
import {CarService} from "../../_services/car-service";
import {Car} from "../../_models/Car";
import { faCheckDouble, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {
  cars:Car[] = [];
  iconDoubleCheck = faCheckDouble;
  iconCircleX = faTimesCircle;

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.carService.retrieveCars().subscribe(cars => {
      this.cars = cars;
      console.log(cars);
      console.log(this.cars);
    });
  }

  isCarFree(status: string): boolean {
    return status === 'FREE';
  }
}
