package com.capgemini.pecunia.passbookmgmt.controller;

import java.util.Map;

import com.capgemini.pecunia.accountmgmt.exceptions.ChequeBouncedException;
import com.capgemini.pecunia.passbookmgmt.service.PassbookServiceImpl;
import com.capgemini.pecunia.passbookmgmt.util.PassbookUtil;

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
public class PassbookRestController {
    private static final Logger Log = LoggerFactory.getLogger(PassbookRestController.class);

    @Autowired
    private PassbookServiceImpl service;

    @PostMapping("/account/summary")
    public ResponseEntity<String> accountSummary(@RequestBody Transaction request) {
        Transaction transaction = PassbookUtil.accountSummary(request);
        service.accountSummary(transAccountId);
        return new ResponseEntity<String>(" Successful", HttpStatus.OK);

    }
    
    @PostMapping("/update/passbook")
    public ResponseEntity<String> updatePassbook(@RequestBody Map<String, Object> request) {
        Transaction transaction = PassbookUtil.updatePassbook(request);
        service.updatePassbook(transAccountId);
        return new ResponseEntity<String>("Updated successful", HttpStatus.OK);

    }
    
    
}
