import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {Brand} from '../model/brand';
import {ProductType} from '../model/product-type';
import {Product} from '../model/product';

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

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
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
}
