import { Component, OnInit } from '@angular/core';
import { ShopService } from './services/shop.service';
import { lastValueFrom } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import {MatGridListModule} from '@angular/material/grid-list';
import { ShopModel } from '../../models/shop.model';
import { ShopProductModel } from '../../models/shopProduct.model';
import {MatTableModule} from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import { CartService } from '../cart/services/cart.service';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule, HttpClientModule, MatGridListModule, MatTableModule, MatButtonModule,
    MatFormFieldModule, MatInputModule, FormsModule, MatDialogModule],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent implements OnInit {

  shopList?:Array<ShopModel>;
  cols:number = 4;
  shopProductList: Array<ShopProductModel> = [];
  displayedColumns: string[] = ['name', 'cost', 'actions'];
  selectedShop?:ShopModel;
  snackDuration = 3;

  constructor(private shopService:ShopService, private cartService:CartService,
    private _snackBar: MatSnackBar){

  }

  async ngOnInit() {
    let request = await lastValueFrom(this.shopService.getAllShops());
    console.log("request", request);
    
    if(request.data){
      this.shopList = request.data;
      if(this.shopList!.length < 4){
        this.cols = this.shopList!.length
      }
    } else {
      console.log(request.data.message);
    }
  }

  showShopProducts(selSshop:ShopModel){
    this.selectedShop = selSshop;
    this.shopProductList = this.shopList!.find(shop => shop.id === selSshop.id)!.productsList!;
    // this.shopProductList.forEach(product => product.quantity = 0)
  }
  
  addProduct(product:ShopProductModel){
    if(product.quantity == undefined || product.quantity === 0){
      // show alert
      this._snackBar.open("Favor ingrese una cantidad", undefined, {
        duration: this.snackDuration * 1000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: 'snack_error'
      });
    } else {
      this.cartService.addProduct(this.selectedShop!, product);  
    }
    product.quantity = undefined;
    
    
  }

}
