import {Booking} from "./Booking";

export class User {
  id: number;
  userName: string;
  firstName: string;
  lastName: string;
  bookings: Array<Booking>;
  active: boolean;
  token: string;
  email: string;
  mobile: string;
}
