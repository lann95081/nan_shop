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
  productType: ProductType[];
  products: Product[];

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
    this.productService.getAllProduct().subscribe(data => {
      this.products = data;
    });
  }

}
