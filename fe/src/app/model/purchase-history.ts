import {User} from './user';

export interface PurchaseHistory {
  purchaseHistoryId: number;
  codeBill: string;
  orderDate: string;
  total: number;
  user: User;
}
