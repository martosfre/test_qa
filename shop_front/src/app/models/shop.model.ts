import { ShopProductModel } from "./shopProduct.model";

export class ShopModel{
    id!:number;
    name?:string;
    address?:string;
    productsList?: Array<ShopProductModel>
    
}