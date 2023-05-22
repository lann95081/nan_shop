import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Brand} from '../model/brand';
import {Product} from '../model/product';

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
}
