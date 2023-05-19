import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) {
  }

  getAllBrand(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/list/brand');
  }

  getAllType(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/list/type');
  }

  getAllProduct(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/list');
  }
}
