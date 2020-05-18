package com.capgemini.pecunia.transactionmgmt.exception;

public class ChequeBouncedException extends RuntimeException {
	public ChequeBouncedException(String msg) {
		super(msg);
	}
}
