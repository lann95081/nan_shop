import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShareService {

  constructor() { }

  private subject = new Subject<any>();
  private itemCount: BehaviorSubject<any> = new BehaviorSubject<number>(0);
  private itemTotal: BehaviorSubject<any> = new BehaviorSubject<number>(0);

  getCount(): Observable<any> {
    return this.itemCount.asObservable();
  }

  setCount(count: number): void {
    this.itemCount.next(count);
  }
  getTotal(): Observable<any> {
    return this.itemTotal.asObservable();
  }

  setTotal(total: number): void {
    this.itemTotal.next(total);
  }

  sendClickEvent() {
    this.subject.next();
  }

  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }
}
