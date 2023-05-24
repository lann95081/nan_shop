import {Component, OnInit} from '@angular/core';
import {CartDetailDto} from '../dto/cart-detail-dto';
import {ProductService} from '../service/product.service';
import {TokenStorageService} from '../service/token-storage.service';
import {UserService} from '../service/user.service';
import {ShareService} from '../service/share.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartDetailDtos: CartDetailDto[];
  username: string;
  userId: number;
  sum = 0;
  total = 0;
  ship = 30;


  constructor(private productService: ProductService,
              private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.username = this.tokenStorageService.getUser()?.username;
    this.userService.findUserEmail(this.username).subscribe(next => {
      this.userId = next?.userId;
      this.getAllCartDetail(this.userId);
    });
  }

  minus(cartDetailId: number) {
    for (const items of this.cartDetailDtos) {
      if (items.cartDetailId === cartDetailId) {
        if (items.amount <= 1) {
          break;
        } else {
          items.amount--;
          this.productService.updateAmount(items.amount, cartDetailId).subscribe(() => {
          });
          this.sum -= items.price;
          this.total = this.sum + this.ship;
          break;
        }
      }
    }
  }

  plus(cartDetailId: number) {
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.cartDetailDtos.length; i++) {
      if (this.cartDetailDtos[i].cartDetailId === cartDetailId) {
        this.cartDetailDtos[i].amount++;
        this.productService.updateAmount(this.cartDetailDtos[i].amount, cartDetailId).subscribe(() => {
        }, error => {
        });
        this.sum += this.cartDetailDtos[i].price;
        this.total = this.sum + this.ship;
        break;
      }
    }
  }

  getTotal() {
    for (const element of this.cartDetailDtos) {
      this.sum += element.amount * element.price;
    }
    this.total = this.sum + this.ship;
  }

  getAllCartDetail(userId: number) {
    this.productService.getAllCartDetail(userId).subscribe(data => {
      this.cartDetailDtos = data;
      if (this.sum === 0) {
        this.getTotal();
      }
    });
  }

  deleteCartDetail(cartId: number, productId: number, productName: string, cartDetailId: number) {
    this.productService.deleteCartDetail(cartId, productId).subscribe(() => {
      this.shareService.sendClickEvent();
      this.productService.getAllCartDetail(this.userId).subscribe(data => {
        this.cartDetailDtos = data;
      });
      Swal.fire({
        title: 'Thông báo!',
        text: 'Bạn vừa xoá mặt hàng ' + productName,
        icon: 'success',
        confirmButtonText: 'OK'
      });
      for (const item of this.cartDetailDtos) {
        if (item.cartDetailId === cartDetailId) {
          this.sum -= item.price * item.amount;
          this.total = this.sum + this.ship;
          break;
        }
      }
    });
  }
}
