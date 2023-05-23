import {Cart} from './cart';
import {Product} from './product';

export interface CartDetail {
  cartDetailId?: number;
  amount?: number;
  cartId: Cart;
  productId: Product;
}
