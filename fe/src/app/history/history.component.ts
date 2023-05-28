import {Component, OnInit} from '@angular/core';
import {PurchaseHistory} from '../model/purchase-history';
import {ProductService} from '../service/product.service';
import {UserService} from '../service/user.service';
import {TokenStorageService} from '../service/token-storage.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  username = '';
  purchaseHistories: PurchaseHistory[];

  constructor(private productService: ProductService,
              private userService: UserService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.username = this.tokenStorageService.getUser().username;
    this.userService.findUserEmail(this.username).subscribe(next => {
      this.productService.findAllHistory(next.userId).subscribe(data => {
        console.log('nan khung');
        this.purchaseHistories = data;
      });
    });
  }

}
