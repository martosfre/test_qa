import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private httpClient: HttpClient) { }

  createOrder(newOrder:any):Observable<any>{
    return this.httpClient.post("/api/v1/order", newOrder);
  }

}
