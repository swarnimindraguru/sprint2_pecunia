package com.capgemini.pecunia.transactionmgmt.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.capgemini.pecunia.accountmgmt.util.DateUtil;
import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;
import com.capgemini.pecunia.transactionmgmt.exception.*;

public class TransactionUtil {


    public static Transaction convertToTransactionUsingSlip(Map<String, Object> request) {
        Transaction transaction = new Transaction();
        String transAccountId = (String) request.get("transAccountId");
        transaction.setTransAccountId(transAccountId);
        String transAmountText = request.get("transAmount").toString();
        double transAmount = Double.parseDouble(transAmountText);
        transaction.setTransAmount(transAmount);
        //String transFrom = (String)request.get("transFrom");
        //transaction.setTransFrom(transFrom);

        return transaction;
    }


    public static Transaction convertToTransactionUsingCheque(Cheque cheque) {
        Transaction transaction = new Transaction();
        transaction.setTransAccountId(cheque.getChequeAccountNum());
        transaction.setTransAmount(cheque.getAmount());
        transaction.setTransDate(new Date());
        return transaction;
    }

    public static Cheque convertToCheque(Map<String, Object> request) {
        Cheque cheque = new Cheque();
        int chequeNum = (int) request.get("chequeNum");
        cheque.setChequeNum(chequeNum);
        String chequeAccountNum = (String) request.get("chequeAccountNum");
        cheque.setChequeAccountNum(chequeAccountNum);
        String chequeHolderName = (String) request.get("chequeHolderName");
        cheque.setChequeHolderName(chequeHolderName);
        String chequeBankName = (String) request.get("chequeBankName");
        cheque.setChequeBankName(chequeBankName);
        String chequeIFSC = (String) request.get("chequeIFSC");
        cheque.setChequeIFSC(chequeIFSC);
        String issueDateText = (String) request.get("issueDate");
        Date issueDate=DateUtil.toDate(issueDateText);
        cheque.setIssueDate(issueDate);
        String amountText =  request.get("amount").toString();
        Double amount=Double.parseDouble(amountText);
        cheque.setAmount(amount);
        return cheque;
    }


    public static void validateCreditSlip(Transaction transaction) {

        if (transaction.transAccountId.length() != 12
                && !(transaction.getTransAmount() >= 100 && transaction.getTransAmount() <= 100000)) {
            throw new IncorrectSlipDetailsException("Slip details are Invalid");
        }
    }

    public static void validateDebitSlip(Transaction transaction) {

        if (transaction.transAccountId.length() != 12) {
            throw new IncorrectSlipDetailsException("Slip details are Invalid");
        }
    }


    public static void validateCheque(Cheque cheque, Transaction transaction) {
        Date issuedDate = cheque.getIssueDate();
        Calendar issuedCalendar=Calendar.getInstance();
        issuedCalendar.setTime(issuedDate);
        int issuedMonth = issuedCalendar.get(Calendar.MONTH);
        int issuedYear = issuedCalendar.get(Calendar.YEAR);
        Calendar currentCalendar = Calendar.getInstance();
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentYear = currentCalendar.get(Calendar.YEAR);

        System.out.println("current year="+currentYear+" issued year="+issuedDate.getYear());

        if (currentYear - issuedYear > 1) {
            throw new ChequeBouncedException("Cheque is expired");
        }
        if (currentYear == issuedYear && currentMonth - issuedMonth > 3) {
            throw new ChequeBouncedException("Cheque is expired");
        }

        if (currentYear != issuedYear && currentMonth + 12 - issuedMonth > 3) {
            throw new ChequeBouncedException("Cheque is expired");
        }

        if (cheque.getChequeBankName() == null || cheque.getChequeBankName().isEmpty()) {
            throw new IncorrectChequeDetailsException("Cheque details are Incomplete");
        }

        String chequenum = String.valueOf(cheque.getChequeNum());
       
        if (cheque.getChequeAccountNum().length() != 12) {
            throw new InvalidChequeException("Cheque Account Number is incorrect");
        }
        System.out.println("Transaction Amount " + transaction.getTransAmount());
        if (!(transaction.getTransAmount() >= 100 && transaction.getTransAmount() <= 200000)) {
            throw new InvalidTransactionAmountException("Transaction Amount is not in limit");
        }
        if (chequenum.length() != 6) {
            throw new InvalidChequeLengthException("Cheque Number length is incorrect ");
        }

        if (!(cheque.getChequeIFSC().length() == 10) && (cheque.getChequeIFSC().matches("\\w+"))) {

            throw new InvalidChequeIFSCException("Invalid IFSC");


        }
    }
}
