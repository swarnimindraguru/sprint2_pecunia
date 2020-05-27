package com.capgemini.pecunia.transactionmgmt.service;

import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;

public interface ITransactionService {
	 double getBalance(Account account);

	 boolean updateBalance(Account account, double balance);

	 Account getAccountById(String accNumber);

	 String creditUsingSlip(Transaction transaction);

	 String debitUsingSlip(Transaction transaction);

	 String creditUsingCheque(Transaction transaction, Cheque cheque);

	 String debitUsingCheque(Transaction transaction, Cheque cheque);

}
