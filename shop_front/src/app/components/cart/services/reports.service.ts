import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  constructor(private httpClient: HttpClient) { }

  getOrdersReport():Observable<any>{
    return this.httpClient.get("/api/v1/reports/item-report/pdf");
  }
}
