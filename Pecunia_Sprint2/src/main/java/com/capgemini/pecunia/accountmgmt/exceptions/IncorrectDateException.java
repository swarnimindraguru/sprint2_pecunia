package com.capgemini.pecunia.accountmgmt.exceptions;

public class IncorrectDateException extends RuntimeException{

    public IncorrectDateException(String msg){
        super(msg);
    }
}
