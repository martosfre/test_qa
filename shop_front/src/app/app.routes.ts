import { Routes } from '@angular/router';
import { ShopComponent } from './components/shop/shop.component';
import { LoginComponent } from './components/login/login.component';
import { CartComponent } from './components/cart/cart.component';
import { AuthGuard } from './auth/auth.guard';
import { LayoutComponent } from './components/layout/layout.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';

export const routes: Routes = [
    // { path: '**', redirectTo: 'shop' },
    { path: 'login', component: LoginComponent },
    // { path: 'cart', component: CartComponent, canActivate:[AuthGuard] },
    // { path: 'shop', component: ShopComponent, canActivate:[AuthGuard] },
    {
        path: '',
        component: LayoutComponent,
        children: [
          { path: 'cart', component: CartComponent, canActivate:[AuthGuard] },
        //   { path: 'shop', component: ShopComponent },
        //   { path: 'forbidden', component: ForbiddenComponent },
        ]
    }
];
