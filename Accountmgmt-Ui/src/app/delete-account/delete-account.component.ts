import { Component, OnInit } from '@angular/core';
import { AccountServiceService } from '../service/account-service.service';
import { Account } from '../Model/account';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  service:AccountServiceService;

  constructor(service:AccountServiceService) { 
    this.service=service;

  }


  ngOnInit(): void {
  }
  msg:string;
  deleteAccount(deleteForm:any){
    //let details=form.value;

    let accountNumber=deleteForm.value.accountId;
    console.log(accountNumber);
    let model : Account = new Account();
    model.accountId=accountNumber;
    console.log(model.accountId);
    let result=this.service.deleteAccount(model); // delete from the db
    result.subscribe((msg:string)=>{
      this.msg=msg;
    },
    err=>{
    console.log("err="+err);
    } );
    deleteForm.reset();
  }

}
