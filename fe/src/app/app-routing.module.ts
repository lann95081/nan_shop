import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainComponent} from './main/main.component';
import {LoginComponent} from './login/login.component';
import {CartComponent} from './cart/cart.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';
import {AuthGuard} from './security/auth.guard';
import {HistoryComponent} from './history/history.component';
import {UserGuard} from './security/user.guard';


const routes: Routes = [
  {
    path: '',
    component: MainComponent
  },
  {
    canActivate: [AuthGuard],
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'nan',
    component: CartComponent
  },
  {
    path: 'product/:productId',
    component: ProductDetailComponent
  },
  {
    path: 'history',
    component: HistoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
