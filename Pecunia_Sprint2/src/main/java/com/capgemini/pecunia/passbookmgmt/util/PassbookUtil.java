package com.capgemini.pecunia.passbookmgmt.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.capgemini.pecunia.accountmgmt.exceptions.ChequeBouncedException;
import com.capgemini.pecunia.passbookmgmt.exception.WrongAccountNumberException;
import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;
import com.capgemini.pecunia.transactionmgmt.exception.*;

public class PassbookUtil 
{
	 public static boolean validate(String transAccountId)
	 {
		 Transaction transaction = new Transaction();
   
        if (transaction.getTransAccountId().length() != 12) {
            throw new WrongAccountNumberException(" Account Number is incorrect");
        }
        
        return false;
    }

	 public static Transaction accountSummary(Map<String, Object> request) {
		 Transaction transaction = new Transaction();
	        String transAccountId = (String) request.get("transAccountId");
	        transaction.setTransAccountId(transAccountId);
	        return transaction;
	 } 
	 
	 public static Transaction updatePassbook(Map<String, Object> request) {
		 Transaction transaction = new Transaction();
	        String transAccountId = (String) request.get("transAccountId");
	        transaction.setTransAccountId(transAccountId);
	        return transaction;
	 } 
	 
	 
}
