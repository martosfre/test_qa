import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService {

  constructor(private httpClient: HttpClient) { }

  createProvider(documentNumber:string, name:string):Observable<any>{
    return this.httpClient.post("/api/v1/providers", {
      documentNumber:documentNumber,
      name: name
    });
  }

  getAll():Observable<any>{
    return this.httpClient.get("/api/v1/providers");
  }
}
