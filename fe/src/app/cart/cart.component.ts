import {Component, OnInit} from '@angular/core';
import {CartDetailDto} from '../dto/cart-detail-dto';
import {ProductService} from '../service/product.service';
import {TokenStorageService} from '../service/token-storage.service';
import {UserService} from '../service/user.service';
import {ShareService} from '../service/share.service';
import Swal from 'sweetalert2';
import {render} from 'creditcardpayments/creditCardPayments';
import {User} from '../model/user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartDetailDto: CartDetailDto[];
  username: string;
  userId: number;
  sum = 0;
  total = 0;
  ship = 30;
  user: User;

  constructor(private productService: ProductService,
              private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private shareService: ShareService,
              private router: Router) {

  }

  ngOnInit(): void {
    this.username = this.tokenStorageService.getUser()?.username;
    this.userService.findUserEmail(this.username).subscribe(data => {
      this.userId = data?.userId;
      this.productService.getAllCartDetail(this.userId).subscribe(next => {
        this.cartDetailDto = next;
        if (this.sum === 0) {
          this.getTotal();
        }
        this.render();
      });
    });
  }

  getTotal() {
    for (const key of this.cartDetailDto) {
      this.sum += key.amount * key.price;
    }
    this.total = this.sum + this.ship;
    this.shareService.setTotal(this.total);
  }

  minus(cartDetailId: number) {
    for (const items of this.cartDetailDto) {
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
    for (let i = 0; i < this.cartDetailDto.length; i++) {
      if (this.cartDetailDto[i].cartDetailId === cartDetailId) {
        this.cartDetailDto[i].amount++;
        if (this.cartDetailDto[i].amount > this.cartDetailDto[i].amountt) {
          Swal.fire({
            title: 'Thông báo!',
            text: 'Bạn không thể thêm sản phẩm vào giỏ hàng vì số lượng sản phẩm không còn trong kho.',
            icon: 'error',
            confirmButtonColor: 'darkgreen',
            confirmButtonText: 'OK'
          });
          this.cartDetailDto[i].amount--;
          break;
        } else {
          this.productService.updateAmount(this.cartDetailDto[i].amount, cartDetailId).subscribe(() => {
          }, error => {
          });
          this.sum += this.cartDetailDto[i].price;
          this.total = this.sum + this.ship;
          break;
        }
      }
    }
  }

  render() {
    render({
      id: '#myPaypalButtons',
      currency: 'USD',
      value: (this.total / 23000).toFixed(2),
      onApprove: (details) => {
        Swal.fire({
          title: 'Thông báo!',
          text: 'Thanh toán thành công',
          icon: 'success',
          confirmButtonText: 'OK'
        });
        this.productService.setCart(this.userId).subscribe(() => {
          this.productService.getAllCartDetail(this.userId).subscribe(data => {
            this.cartDetailDto = data;
          });
          this.shareService.sendClickEvent();
        });
        this.router.navigateByUrl('/nan');
      }
    });
  }

  deleteCartDetail(cartId: number, productId: number, productName: string, cartDetailId: number) {
    this.productService.deleteCartDetail(cartId, productId).subscribe(() => {
      this.shareService.sendClickEvent();
      this.productService.getAllCartDetail(this.userId).subscribe(data => {
        this.cartDetailDto = data;
      });
      Swal.fire({
        title: 'Thông báo!',
        text: 'Bạn vừa xoá mặt hàng ' + productName,
        icon: 'success',
        confirmButtonText: 'OK'
      });
      for (const item of this.cartDetailDto) {
        if (item.cartDetailId === cartDetailId) {
          this.sum -= item.price * item.amount;
          this.total = this.sum + this.ship;
          break;
        }
      }
    });
  }
}
