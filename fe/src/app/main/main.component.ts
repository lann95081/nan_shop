import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {Brand} from '../model/brand';
import {ProductType} from '../model/product-type';
import {Product} from '../model/product';
import Swal from 'sweetalert2';
import {ShareService} from '../service/share.service';
import {TokenStorageService} from '../service/token-storage.service';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  brands: Brand[];
  products: Product[];
  nameSearch: '';
  brandId = 0;
  userId: number;
  username: string;

  constructor(private productService: ProductService,
              private shareService: ShareService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.getUser();
    this.findAllBrand();
    this.findAllProduct();
  }

  findAllBrand() {
    this.productService.getAllBrand().subscribe(data => {
      this.brands = data;
    });
  }

  findAllProduct() {
    if (this.nameSearch === undefined) {
      this.nameSearch = '';
    }
    if (this.brandId === undefined) {
      this.brandId = 0;
      this.productService.getAllByName(this.nameSearch).subscribe(data => {
        this.products = data;
      });
    } else {
      this.productService.getAllByNameAndBrand(this.nameSearch, this.brandId).subscribe(data => {
        this.products = data;
      });
    }
  }

  search() {
    this.ngOnInit();
  }

  addToCart(productId: number) {
    this.productService.addToCart(this.userId, productId, 1).subscribe(() => {
      Swal.fire({
        title: 'Thông báo!',
        text: 'Thêm sản phẩm vào giỏ hàng thành công',
        icon: 'success',
        confirmButtonText: 'OK'
      });
      this.productService.getAllCartDetail(this.userId).subscribe(data => {
        this.shareService.setCount(data.length);
      });
    }, error => {
      if (!this.tokenStorageService.getToken()) {
        Swal.fire({
          title: 'Thông báo!',
          text: 'Bạn phải đăng nhập trước khi muốn mua hàng',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
      this.router.navigateByUrl('/login');
    });
  }

  getUser() {
    this.username = this.tokenStorageService.getUser()?.username;
    this.userService.findUserEmail(this.username).subscribe(data => {
      this.userId = data?.userId;
    });
  }
}
