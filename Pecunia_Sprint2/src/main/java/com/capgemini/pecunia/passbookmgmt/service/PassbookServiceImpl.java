package com.capgemini.pecunia.passbookmgmt.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.capgemini.pecunia.accountmgmt.util.AccountUtil;
import com.capgemini.pecunia.passbookmgmt.dao.PassbookDao;
import com.capgemini.pecunia.passbookmgmt.exception.WrongAccountNumberException;
import com.capgemini.pecunia.passbookmgmt.util.PassbookUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.pecunia.accountmgmt.dao.IAccountDao;
import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.transactionmgmt.dao.ChequeDao;
import com.capgemini.pecunia.transactionmgmt.dao.TransactionDao;
import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;
import com.capgemini.pecunia.transactionmgmt.exception.AccountNotFoundException;
import com.capgemini.pecunia.transactionmgmt.exception.InvalidTransactionAmountException;
import com.capgemini.pecunia.transactionmgmt.service.TransactionServiceImpl;
import com.capgemini.pecunia.transactionmgmt.util.TransactionUtil;


@Service
@Transactional
public class PassbookServiceImpl implements IPassbookService {
	  
	    @Autowired
	    private IAccountDao accountDao;
	    @Autowired
	    private TransactionDao transactionDao;
	    @Autowired
	    private PassbookDao passbookDao;
	    @Autowired
	    private TransactionServiceImpl transService;
   
	    @Override
	    public List<Transaction> accountSummary(String transAccountId) {
	     if (PassbookUtil.validate(transAccountId)) {
	    	Optional<Transaction> optional=transactionDao.findById(transAccountId);
	 	   if(optional.isPresent())
	 		{
	 			
	 			List<Transaction> list=(List<Transaction>) optional.get();
	 			return list;
	 		}
	 		else
	 		{
	 			throw new AccountNotFoundException("no account exist");
	 		}
	    }   
	     return null;
	    }
	    
	    
	    @Override
	   public boolean updatePassbook(String transAccountId)
	   {
	    if (PassbookUtil.validate(transAccountId)) {
	    		 Optional<Transaction> optional=transactionDao.findById(transAccountId);
	  	 	 if(optional.isPresent())
	  	 		{
	  	 		 transService.updateBalance(null, 0);
	  	 		
	  	        return true;
	  	 			
	  	 		}
	  	 		else
	  	 		{
	  	 			throw new AccountNotFoundException("no account exist");
	  	 		}
	  	    }   
	  	     return false;
	    	 
	   }
	    
	    
	    
	    
}
