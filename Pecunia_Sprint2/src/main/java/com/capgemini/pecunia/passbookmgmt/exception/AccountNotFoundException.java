package com.capgemini.pecunia.passbookmgmt.exception;

public class AccountNotFoundException extends RuntimeException{
	public AccountNotFoundException(String msg) {
		super(msg);
	}
}
