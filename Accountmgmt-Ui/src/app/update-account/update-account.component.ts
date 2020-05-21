import { Component, OnInit } from '@angular/core';
import { AccountServiceService } from '../service/account-service.service';
import { Account } from '../Model/account';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-update-account',
  templateUrl: './update-account.component.html',
  styleUrls: ['./update-account.component.css']
})
export class UpdateAccountComponent implements OnInit {

  service:AccountServiceService;

  constructor(service:AccountServiceService) { 
    this.service=service;

  }

  ngOnInit(): void {
  }
  account:Account;
  update:string;
  msg:any;
  countryHasError=true;
  updateAccount(updateForm:any){
    let details=updateForm.value;
   let accountId=details.accountNumber;
   console.log(accountId);
    let customerName=details.customerName;
    this.update="changename";
    
    
    this.account=new Account();
    this.account.accountId=accountId;
    this.account.customerName=customerName;
    console.log(this.account.customerName);
    

    
    let result:Observable<any>=this.service.updateAccount(this.update,this.account); // adding to the store
    result.subscribe((accountResponse:any)=>{
      this.msg=accountResponse;
    },
    err=>{
    console.log("err="+err);
    } );
    updateForm.reset();
  }
  updateCustomerContact(updateContact:any){
    let details=updateContact.value;
   let accountId=details.accountNumber;
   console.log(accountId);
   let customerContact=details.customerContact;
    
    
    this.account=new Account();
    this.account.accountId=accountId;
    this.account.customerContact=customerContact;
    console.log(this.account.customerContact);
    this.update="updatecontact";

    
    let result:Observable<any>=this.service.updateAccount(this.update,this.account); // adding to the store
    result.subscribe((accountResponse:any)=>{
      this.msg=accountResponse;
    },
    err=>{
    console.log("err="+err);
    } );
    updateContact.reset();
  }

}
