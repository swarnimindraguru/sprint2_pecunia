package com.capgemini.pecunia.passbookmgmt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.transactionmgmt.entities.Transaction;

public interface PassbookDao extends JpaRepository<Transaction,String> {

}
