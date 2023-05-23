import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Brand} from '../model/brand';
import {Product} from '../model/product';
import {CartDetailDto} from '../dto/cart-detail-dto';
import {CartDetail} from '../model/cart-detail';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) {
  }

  getAllBrand(): Observable<Brand[]> {
    return this.httpClient.get<Brand[]>('http://localhost:8080/api/brand');
  }

  getAllByName(nameSearch: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/product?nameSearch=' + nameSearch);
  }

  getAllByNameAndBrand(nameSearch: string, brandId: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/product?nameSearch=' + nameSearch + '&brandId=' + brandId);
  }

  getAllProduct(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/product');
  }

  findByProductId(productId: number): Observable<Product> {
    return this.httpClient.get<Product>('http://localhost:8080/api/product/' + productId);
  }

  getAllCartDetail(userId: number): Observable<CartDetailDto[]> {
    return this.httpClient.get<CartDetailDto[]>('http://localhost:8080/api/cart/' + userId);
  }

  addToCart(userId: number, productId: number, amount: number): Observable<CartDetail[]> {
    return this.httpClient.get<CartDetail[]>('http://localhost:8080/api/cart/addToCart/' + userId + '/' + productId + '/' + amount);
  }

  updateAmount(amount: number, cartDetailId: number): Observable<any> {
    return this.httpClient.get<any>('http://localhost:8080/api/cart/updateAmount/' + amount + '/' + cartDetailId);
  }

  deleteCartDetail(cartId: number, productId: number): Observable<any> {
    return this.httpClient.delete('http://localhost:8080/api/cart/deleteCartDetail/' + cartId + '/' + productId);
  }
}
