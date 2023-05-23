import {User} from './user';

export interface Cart {
  cartId?: number;
  date: string;
  userId?: User;
}
