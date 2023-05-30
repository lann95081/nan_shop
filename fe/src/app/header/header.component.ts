import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../service/token-storage.service';
import {ShareService} from '../service/share.service';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {ProductService} from '../service/product.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username?: string;
  name?: string;
  role?: string;
  isLoggedIn = false;
  count = 0;

  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private userService: UserService,
              private router: Router,
              private productService: ProductService) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
    this.shareService.getCount().subscribe(count => {
      this.count = count;
    });
  }

  ngOnInit(): void {
    this.loadHeader();
  }

  loader() {
    this.shareService.getClickEvent();
    this.shareService.getCount().subscribe(data => {
      this.count = data;
    });
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().username;
      this.isLoggedIn = this.username != null;
      this.findNameUser();
    } else {
      this.isLoggedIn = false;
    }
  }

  findNameUser(): void {
    this.userService.findUserEmail(this.username).subscribe(data => {
      this.name = data.name;
      this.productService.getAllCartDetail(data?.userId).subscribe(item => {
        this.count = item?.length;
      });
    });
  }

  logOut() {
    this.tokenStorageService.signOut();
    this.ngOnInit();
    Swal.fire({
      title: 'Thông báo!',
      text: 'Đăng xuất thành công',
      icon: 'success',
      confirmButtonText: 'OK'
    });
    this.shareService.setCount(0);
    this.router.navigateByUrl('');
  }
}
