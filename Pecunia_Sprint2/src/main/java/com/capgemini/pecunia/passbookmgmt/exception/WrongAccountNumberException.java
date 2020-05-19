package com.capgemini.pecunia.passbookmgmt.exception;

public class WrongAccountNumberException extends RuntimeException{
	public WrongAccountNumberException(String msg) {
		super(msg);
	}
}
