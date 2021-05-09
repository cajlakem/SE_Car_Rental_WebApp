import {Booking} from './Booking';

export class User {
  id: string;
  userName: string;
  firstName: string;
  lastName: string;
  bookings: Array<Booking>;
  active: boolean;
  token: string;
  email: string;
  mobile: string;
  password: string;
}
