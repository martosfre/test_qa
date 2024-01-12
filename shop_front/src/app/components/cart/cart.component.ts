import { Component, OnInit, ViewChild } from '@angular/core';
import { CartService } from './services/cart.service';
import { lastValueFrom } from 'rxjs';
import { ClientModel } from '../../models/client.model';
import { MatCardModule } from '@angular/material/card';
import { MatTable, MatTableModule } from '@angular/material/table';
import { ShopProductModel } from '../../models/shopProduct.model';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ShopComponent } from '../shop/shop.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import { PersonService } from './services/person.service';
import { ProvidersService } from './services/providers.service';
import { ShopModel } from '../../models/shop.model';
import { OrdersService } from './services/orders.service';


@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTableModule, MatButtonModule,
    ReactiveFormsModule, MatInputModule, MatDialogModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{

  constructor(private cartService: CartService, private router: Router,
    public dialog: MatDialog, private _snackBar: MatSnackBar,
    private personService:PersonService, private providerService:ProvidersService,
    private ordersService:OrdersService) { }

  async ngOnInit() {
    let request = await lastValueFrom(this.personService.getAllClients());
    this.allClients = request.data;

    let providerRequest = await lastValueFrom(this.providerService.getAll());
    console.log("providerRequest", providerRequest);
    
    this.allProviders = providerRequest.data;
  }

  client?: ClientModel;
  displayedColumns: string[] = ['shop', 'name', 'quantity', 'unitCost', 'totalCost', 'actions'];
  shopProductList: Array<ShopModel> = [];
  allProductsList: Array<any> = [];
  subtotal?: number;
  iva?: number;
  total?: number;
  @ViewChild(MatTable) table?: MatTable<ShopProductModel>;
  snackDuration = 3;
  allClients:Array<ClientModel> = [];
  allProviders:Array<any> = [];

  clientForm = new FormGroup({
    documentNumber: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    email: new FormControl('')
  });

  providerForm = new FormGroup({
    documentNumber: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required)
  });

  goToShop() {
    this.router.navigate(['/shop']);
  }

  validateClientFormField(field: string) {
    return this.clientForm.get(field)?.errors &&
      (this.clientForm.get(field)?.dirty || this.clientForm.get(field)?.touched)
  }

  validateProviderFormField(field: string) {
    return this.clientForm.get(field)?.errors &&
      (this.providerForm.get(field)?.dirty || this.providerForm.get(field)?.touched)
  }

  openSelectProducts() {
    const dialogRef = this.dialog.open(ShopComponent);

    dialogRef.afterClosed().subscribe(result => {
      this.allProductsList = [];
      this.shopProductList = this.cartService._cartItems;
      this.cartService._cartItems.forEach(shop => {
        shop.productsList?.forEach(product => {
          this.allProductsList.push({
            shop: shop.name,
            name: product.name,
            quantity: product.quantity,
            unitCost: product.cost,
            totalCost: product!.cost! * product!.quantity!
          })
        })
      });
      
      console.log("allProductsList", this.allProductsList);
      this.subtotal = this.allProductsList.reduce((sum, curr) => sum + curr.totalCost, 0);
      this.iva = this.subtotal! * 0.12;
      this.total = this.subtotal! + this.iva;

      this.table!.renderRows();
    });
  }

  async generateOrder(){
    console.log("this.shopProductList", this.shopProductList);
    
    //1 validar forms
    if (!this.clientForm.valid || !this.providerForm.valid) {
      this._snackBar.open("Favor ingrese todos los datos requeridos", undefined, {
        duration: this.snackDuration * 1000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: 'snack_error'
      });
      return;
    }
    // 2 Guardo cliente
    let newPerson = await lastValueFrom(this.personService.createPerson(this.clientForm.value.documentNumber!, 
      this.clientForm.value.name!, this.clientForm.value.email!));
    // 3 Guardo proveedor
    let newProvider = await lastValueFrom(this.providerService.createProvider(this.providerForm.value.documentNumber!, 
      this.providerForm.value.name!));
    // 4 Guardo Orden
    let paramShopList = this.shopProductList.map(shopa => {
      return {
        shopId: shopa.id,
        productsList: shopa.productsList?.map(product => {
          return {
            productId: product.id,
            quantity: product.quantity,
            unitCost: product.cost
          }
        })
      }
    })
    let param = {
      clientId: newPerson.data.id,
      providerId: newProvider.data.id,
      totalCost: this.total,
      shopList: paramShopList
    }

    console.log("param", param)
    await lastValueFrom(this.ordersService.createOrder(param));
    // vacio form
    this.clientForm.reset();
    this.providerForm.reset();
    this.allProductsList = [];

    this._snackBar.open("Orden creada exitosamente", undefined, {
      duration: this.snackDuration * 1000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: 'snack_success'
    });
    this.shopProductList = [];
    this.cartService._cartItems = [];


  }

  changeDocNumber(){
    console.log("cahnge", this.clientForm.value.documentNumber);
    let foundClient = this.allClients.find(client => client.documentNumber == this.clientForm.value.documentNumber)
    if(foundClient){
      this.clientForm.patchValue({
        documentNumber: foundClient?.documentNumber,
        name: foundClient?.name,
        email: foundClient?.email
      })
    }
    
  }

  changeRuc(){
    let foundProvider = this.allProviders.find(provider => provider.documentNumber == this.providerForm.value.documentNumber)
    if(foundProvider){
      this.providerForm.patchValue({
        documentNumber: foundProvider?.documentNumber,
        name: foundProvider?.name
      })
    }
    
  }

}
