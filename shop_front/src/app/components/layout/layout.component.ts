import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatBadgeModule} from '@angular/material/badge';
import { CartService } from '../cart/services/cart.service';
import { LoginService } from '../login/services/login.service';
import { ReportsService } from '../cart/services/reports.service';
import { saveAs } from 'file-saver';
import {MatTooltipModule} from '@angular/material/tooltip';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatToolbarModule, MatButtonModule, MatIconModule,
    MatBadgeModule, MatTooltipModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  constructor(public loginService: LoginService, public cartService:CartService,
    private router: Router, private reportService:ReportsService,private _snackBar: MatSnackBar){
      console.log("loginService.role", loginService.role);
      
    }

  goToCart(){
    this.router.navigate(['/cart']);
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }

  logout(){
    this.loginService.logout();
  }

  async getReport(){
    if(this.loginService.getCurrentRole() != 'ADMIN'){
      this._snackBar.open("El reporte puede ser descargado unicamente por un administrador", undefined, {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: 'snack_error'
      });
      return;
    }
    this.reportService.getOrdersReport().subscribe(
      (blob) => saveAs(blob, 'file.pdf'),
      (error) => {
        const options = 'Failed to download file. Please try again later.';
        // this.snackBar.open(errorMessage, 'Dismiss', { duration: 5000 });
      });
  }

}
