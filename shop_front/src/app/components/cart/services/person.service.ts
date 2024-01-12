import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientModel } from '../../../models/client.model';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private httpClient: HttpClient) { }

  createPerson(documentNumber:string, name:string, email:string):Observable<any>{
    return this.httpClient.post("/api/v1/client", {
      documentNumber:documentNumber,
      name: name,
      email: email
    });
  }

  getAllClients():Observable<any>{
    return this.httpClient.get("/api/v1/client");
  }

}
