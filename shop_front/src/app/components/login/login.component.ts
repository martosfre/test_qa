import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { CartService } from '../cart/services/cart.service';
import { LoginService } from './services/login.service';
import { lastValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatCardModule, ReactiveFormsModule, MatInputModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm = new FormGroup({
    userName: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  constructor(private loginService: LoginService, private cartService: CartService,
    private router: Router, private cookieService:CookieService,
    private _snackBar: MatSnackBar) {
  }

  async doLogin() {
    if (!this.loginForm.valid) {
      return;
    }
    await this.loginService.login(this.loginForm.value.userName!, this.loginForm.value.password!)
    .then((role)=> {
      localStorage.setItem('loggedIn','1');
      console.log("role", role.data);
      
      localStorage.setItem('role',role.data);
      this.loginService.isLoggedIn = true;
      if (this.loginService.nextRoute) {
        this.router.navigate([this.loginService.nextRoute!.path]);
      } else {
        this.router.navigate(['/cart']);
      }
    })
    .catch(()=>{
      this._snackBar.open("Usuario o contraseña incorrectos", undefined, {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: 'snack_error'
      });
      localStorage.setItem('loggedIn','0');
      localStorage.setItem('role', '');
    })
    
  }

  validateFormField(field: string) {
    return this.loginForm.get(field)?.errors &&
      (this.loginForm.get(field)?.dirty || this.loginForm.get(field)?.touched)
  }

}
