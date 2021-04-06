import {User} from './User';
import {Car} from './Car';

export class Booking {
  id: number;
  remark: string;
  label: string;
  startTime: Date;
  endTime: Date;
  price: number;
  car: Car;
  user: User;
  status: string;
  currency: string;

}
