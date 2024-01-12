import { Injectable } from '@angular/core';
import { ShopProductModel } from '../../../models/shopProduct.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShopModel } from '../../../models/shop.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  _cartItems: Array<ShopModel> = [];
  testStr:string = '';

  constructor(private httpClient: HttpClient) { }

  addProduct(addShop:ShopModel, product:ShopProductModel){
    let foundShop = this._cartItems.find(shop => shop.id === addShop.id);
    if(!foundShop){
      foundShop = {
        id: addShop.id,
        name: addShop.name,
        productsList: []
      };
      this._cartItems.push(foundShop);
    }
    // la tienda existe
    let foundItem = foundShop?.productsList?.find(shopProduct => shopProduct.id === product.id)
    if(foundItem){
      // sumo 
      foundItem.quantity = foundItem.quantity! + product.quantity!;
    } else {
      foundShop.productsList!.push({
        id: product.id,
        name: product.name,
        quantity: product.quantity,
        cost: product.cost
      });
    }
  }

  isEmpty(){
    return this._cartItems.length == 0;
  }

  cartSize(){
    return this._cartItems.reduce((accumulator, currentValue) => accumulator + currentValue.productsList!.length, 0 );
  }

  getUserInfo():Observable<any> {
    return this.httpClient.get("/api/v1/client");
  }


}
