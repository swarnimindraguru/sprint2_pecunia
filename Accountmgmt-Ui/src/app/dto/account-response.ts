import { Customer } from './customer';

export class AccountResponse {
    accountId:string;
    accountBranchId:string;
    accountType:string;
    accountStatus:string;
    accountBalance:number;
    accountInterest:number;
    customer:Customer;
}
