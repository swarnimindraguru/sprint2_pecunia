package com.capgemini.pecunia.transactionmgmt.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.capgemini.pecunia.accountmgmt.util.AccountUtil;
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
import com.capgemini.pecunia.transactionmgmt.util.TransactionUtil;


@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private ChequeDao chequeDao;

    @Override
    public double getBalance(Account account) {
        return account.getAccountBalance();
    }

    @Override
    public boolean updateBalance(Account account, double balance) {
        account.setAccountBalance(balance);
        return true;
    }

    @Override
    public Account getAccountById(String accNumber) {
        Optional<Account> optional = accountDao.findById(accNumber);
        if (optional.isPresent()) {
            Account account = optional.get();
            return account;
        }
        throw new AccountNotFoundException("Account not exist for " + accNumber);
    }

    @Override
    public String creditUsingSlip(Transaction transaction) {
        String transId = AccountUtil.generateId("", 6);
        transaction.setTransId(transId);
        TransactionUtil.validateCreditSlip(transaction);
        Account account = getAccountById(transaction.getTransAccountId());
        account.setAccountBalance(account.getAccountBalance() + transaction.getTransAmount());
        transaction.setOption("credit-slip");
        transaction.setTransType("credit");
        accountDao.save(account);
        transactionDao.save(transaction);
        return transId;

    }

    @Override
    public String debitUsingSlip(Transaction transaction) {
        String transId = AccountUtil.generateId("", 6);
        transaction.setTransId(transId);
        TransactionUtil.validateDebitSlip(transaction);
        Account account = getAccountById(transaction.getTransAccountId());
        if (transaction.getTransAmount() > account.getAccountBalance()) {
            throw new InvalidTransactionAmountException("Transaction is not possible");
        }
        account.setAccountBalance(account.getAccountBalance() - transaction.getTransAmount());
        transaction.setOption("debit-slip");
        transaction.setTransType("debit");
        accountDao.save(account);
        transactionDao.save(transaction);
        return transId;
    }


    @Override
    public String creditUsingCheque(Transaction transaction, Cheque cheque) {
        String transId = AccountUtil.generateId("", 6);
        transaction.setTransId(transId);
        String chequeId = AccountUtil.generateId("", 6);
        cheque.setChequeId(chequeId);
        transaction.setOption("credit-cheque");
        transaction.setTransType("credit");
        transaction.setTransDate(new Date());
        TransactionUtil.validateCheque(cheque, transaction);
        Account account = getAccountById(transaction.getTransAccountId());
        account.setAccountBalance(account.getAccountBalance() + transaction.getTransAmount());
        accountDao.save(account);
        chequeDao.save(cheque);
        transactionDao.save(transaction);
        return transId;
    }

    @Override
    public String debitUsingCheque(Transaction transaction, Cheque cheque) {
        String transId = AccountUtil.generateId("", 6);
        transaction.setTransId(transId);
        String chequeId = AccountUtil.generateId("", 6);
        cheque.setChequeId(chequeId);
        transaction.setOption("debit-cheque");
        transaction.setTransType("debit");
        TransactionUtil.validateCheque(cheque, transaction);
        Account account = getAccountById(transaction.getTransAccountId());
        if (transaction.getTransAmount() > account.getAccountBalance()) {
            throw new InvalidTransactionAmountException("Transaction is not possible");
        }
        account.setAccountBalance(account.getAccountBalance() - transaction.getTransAmount());
        accountDao.save(account);
        chequeDao.save(cheque);
        transaction = transactionDao.save(transaction);
        return transId;
    }

}
