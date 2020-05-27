package com.capgemini.pecunia.transactionmgmt.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.pecunia.transactionmgmt.entities.Cheque;
import com.capgemini.pecunia.transactionmgmt.entities.Transaction;
import com.capgemini.pecunia.transactionmgmt.service.TransactionServiceImpl;
import com.capgemini.pecunia.transactionmgmt.util.TransactionUtil;

@RequestMapping("/transactions")
@RestController
public class TransactionRestController {
    private static final Logger Log = LoggerFactory.getLogger(TransactionRestController.class);

    @Autowired
    private TransactionServiceImpl service;

    @PostMapping("/credit/slip")
    public ResponseEntity<String> creditUsingSlip(@RequestBody Map<String, Object> request) {
        Transaction transaction = TransactionUtil.convertToTransactionUsingSlip(request);
        String transactionId=service.creditUsingSlip(transaction);
        String msg="Transaction id="+transactionId+" is successful";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    @PostMapping("/debit/slip")
    public ResponseEntity<String> debitUsingSlip(@RequestBody Map<String, Object> request) {
        Transaction transaction = TransactionUtil.convertToTransactionUsingSlip(request);
        String transactionId=service.debitUsingSlip(transaction);
        String msg="Transaction id="+transactionId+" is successful";
        return new ResponseEntity<>(msg, HttpStatus.OK);

    }
    

    @PostMapping("/credit/cheque")
    public ResponseEntity<String> creditUsingCheque(@RequestBody Map<String, Object> request) {
        Cheque cheque = TransactionUtil.convertToCheque(request);
        Transaction transaction = TransactionUtil.convertToTransactionUsingCheque(cheque);
        String transactionId=service.creditUsingCheque(transaction, cheque);
        String msg="Transaction id="+transactionId+" is successful";
        return new ResponseEntity<>( msg,HttpStatus.OK);
    }


    @PostMapping("/debit/cheque")
    public ResponseEntity<String> debitUsingCheque(@RequestBody Map<String, Object> request) {
        Cheque cheque = TransactionUtil.convertToCheque(request);
        Transaction transaction = TransactionUtil.convertToTransactionUsingCheque(cheque);
        String transactionId=service.debitUsingCheque(transaction, cheque);
        String msg="Transaction id="+transactionId+" is successful";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    
}
