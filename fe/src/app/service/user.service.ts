import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  findUserEmail(email: string): Observable<User> {
    return this.httpClient.get<User>(`http://localhost:8080/api/user/${email}`);
  }
}
