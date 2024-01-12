import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CartService } from '../components/cart/services/cart.service';
import { LoginService } from '../components/login/services/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) { };

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let isLoggedIn = this.loginService.isAuthenticated();
    console.log("next", next);
    console.log('state', state);
    this.loginService.nextRoute = Object.assign({}, next.routeConfig!);
    
    if (isLoggedIn) {
      return true
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

}
