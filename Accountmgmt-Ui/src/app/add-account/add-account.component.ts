import { Component, OnInit } from '@angular/core';
import { Account } from '../Model/account';
import { AccountResponse } from '../dto/account-response';
import { AccountServiceService } from '../service/account-service.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  citys=["Mumbai","chennai","Amroha","Moradabad","kolkata","New Delhi"];
  countries=["India","China","USA","Other"];
  states=["Uttar Pradesh","New Delhi","Madhya Pradesh","Maharastra",];
  service:AccountServiceService;

  constructor(service:AccountServiceService) { 
    this.service=service;

  }


  ngOnInit(): void {
  }
  account:Account;
  msg:string;
 // countryHasError=true;
  addAccount(form:any){
    let details=form.value;
    let branchId=details.branchId;
    let accountType=details.accountType;
    console.log(accountType);
    let accountBalance=details.accountBalance;
    let accountInterest=details.accountInterest;
    let customerName=details.customerName;
    let customerAadhar=details.customerAadhar;
    console.log(customerAadhar);
    let customerPan=details.customerPan;
    let customerContact= details.customerContact;
    let customerGender=details.customerGender;
    console.log(customerContact);
    let cutomerDob=details.customerDob;
    console.log("customer DOB"+cutomerDob);
    let addressLine=details.addressLine;
    let city=details.city;
    console.log(city);
    let state= details.state;
    let country= details.country;
    let zipcode=details.zipcode;
    this.account=new Account();
    this.account.accountBranchId=branchId;
    this.account.accountType=accountType;
    this.account.accountBalance=accountBalance;
    console.log(this.account.accountBalance);
    this.account.accountInterest=accountInterest;
    this.account.customerName=customerName;
    console.log(this.account.customerName);
    this.account.customerAadhar=customerAadhar;
    this.account.customerPan=customerPan;
    this.account.customerGender=customerGender;
    console.log(this.account.customerPan);
    this.account.customerDob=cutomerDob;
    console.log(this.account.customerDob);
    this.account.addressLine=addressLine;
    this.account.addressCity=city;
    this.account.addressState=state;
    this.account.addressCountry=country;
    this.account.addressZipcode=zipcode;

    
    let result:Observable<string>=this.service.addAccount(this.account); // adding to the store
    result.subscribe((msg:string)=>{
      this.msg=msg;
    },
    err=>{
    console.log("err="+err);
    } );
    form.reset();
  }
}
