import {ProductType} from './product-type';
import {Brand} from './brand';

export interface Product {
  productId?: number;
  productName?: string;
  price?: number;
  description?: string;
  img?: string;
  amount?: number;
  productType?: ProductType;
  brand?: Brand;
}
