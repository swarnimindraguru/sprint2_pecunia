package com.capgemini.pecunia.passbookmgmt.service;

import java.util.List;

import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;

public interface IPassbookService {
	List<Transaction> accountSummary(String transAccountId);
	 boolean updatePassbook(String transAccountId);

}
