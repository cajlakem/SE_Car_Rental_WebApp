import {Component, OnInit} from '@angular/core';
import {ServicePoint} from '../_models/ServicePoint';

@Component({
  selector: 'app-servicepoint',
  templateUrl: './servicepoint.component.html',
  styleUrls: ['./servicepoint.component.css']
})
export class ServicepointComponent implements OnInit {
  sp1 = new ServicePoint();
  sp2 = new ServicePoint();
  sp3 = new ServicePoint();
  sp4 = new ServicePoint();

  onMapClicked(event: any) {
    console.log(event);
  }

  ngOnInit(): void {
    this.sp1.name = 'C A R NOW Zentrale';
    this.sp1.lat = 48.189432;
    this.sp1.lng = 16.372219;
    this.sp1.tel = '01 / 504 58 25';
    this.sp2.name = 'C A R NOW Mariahilf';
    this.sp2.lat = 48.196904;
    this.sp2.lng = 16.347812;
    this.sp2.tel = '01 / 667 01 06';
    this.sp3.name = 'C A R NOW Landstraße';
    this.sp3.lat = 48.193635;
    this.sp3.lng = 16.402228;
    this.sp3.tel = '01 / 843 28 52';
    this.sp4.name = 'C A R NOW Messe';
    this.sp4.lat = 48.216584;
    this.sp4.lng = 16.415071;
    this.sp4.tel = '01 / 604 27 69';
  }
}

