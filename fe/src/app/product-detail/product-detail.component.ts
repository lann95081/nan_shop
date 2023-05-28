import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {ProductService} from '../service/product.service';
import {Product} from '../model/product';
import {TokenStorageService} from '../service/token-storage.service';
import {UserService} from '../service/user.service';
import {ShareService} from '../service/share.service';
import Swal from 'sweetalert2';
import {ViewportScroller} from '@angular/common';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,
              private productService: ProductService,
              private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private shareService: ShareService,
              private router: Router,
              private viewportScroller: ViewportScroller) {
  }

  productId: number;
  productDetail: Product;
  num = 1;
  price: number;
  total: number;
  username: string;
  userId: number;

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.productId = +paramMap.get('productId');
      this.findByProductId(this.productId);
    });
    this.getUser();
    this.viewportScroller.scrollToPosition([0, 0]);
  }

  findByProductId(productId: number) {
    this.productService.findByProductId(productId).subscribe(data => {
      this.productDetail = data;
      this.price = data.price;
      this.total = this.price;
    });
  }

  minus() {
    if (this.num <= 1) {
      this.num = 1;
    } else {
      this.num--;
    }
    this.total = this.num * this.price;
  }

  plus() {
    this.num++;
    this.total = this.num * this.price;
  }

  onHead() {
    this.viewportScroller.scrollToPosition([0, 0]);
  }

  getUser() {
    this.username = this.tokenStorageService.getUser()?.username;
    this.userService.findUserEmail(this.username).subscribe(data => {
      this.userId = data?.userId;
    });
  }

  addToCart(productId: number) {
    this.productId = productId;
    this.productService.addToCart(this.userId, this.productId, this.num).subscribe(() => {
      this.productService.getAllCartDetail(this.userId).subscribe(data => {
        this.shareService.setCount(data.length);
      });
      Swal.fire({
        title: 'Thông báo!',
        text: 'Thêm sản phẩm vào giỏ hàng thành công',
        icon: 'success',
        confirmButtonText: 'OK'
      });
      this.router.navigateByUrl('');
    }, error => {
      if (!this.tokenStorageService.getToken()) {
        Swal.fire({
          title: 'Thông báo!',
          text: 'Bạn phải đăng nhập trước khi muốn mua hàng',
          icon: 'error',
          confirmButtonText: 'OK'
        });
        this.router.navigateByUrl('/login');
      }
      if (error.status === 404) {
        Swal.fire({
          title: 'Thông báo!',
          text: 'Bạn không thể thêm sản phẩm vào giỏ hàng vì số lượng sản phẩm không còn trong kho.',
          icon: 'error',
          confirmButtonColor: 'darkgreen',
          confirmButtonText: 'OK'
        });
      }
    });
    this.router.navigateByUrl('/nan');
  }
}
