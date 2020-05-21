import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Account } from '../model/account';
import { AccountResponse } from '../dto/account-response';

@Injectable({
  providedIn: 'root'
})
export class AccountServiceService {

  client:HttpClient ;
  constructor(client:HttpClient ){
  this.client=client;
  }

   baseUrl="http://localhost:8087/accounts";

 /**
  * fires post request to server with account as body
  * 
  *  
  */
   addAccount(account:Account): Observable<string>{
    let url=this.baseUrl+"/add";
    let result:Observable<string>= this.client.post(url,account,{responseType:'text'});
    return result;
   }

   updateAccount(update :string,account:Account): Observable<string>{
    let url=this.baseUrl+"/"+account.accountId+"/"+update;
    let result:Observable<string>= this.client.put(url,account,{responseType:'text'});
    return result;
   }
  
   /**
    * fires delete request to server to delete customer by id mentioned in url
   * 
   */
   deleteAccount(model:Account): Observable<string>{
     console.log(model.accountId);
    let url=this.baseUrl+"/"+model.accountId+"/delete";
     let result:Observable<string>=this.client.put(url,model,{responseType:'text'});
     return result;
   }
}
