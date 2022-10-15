package com.geektrust.backend.exceptions;

public class InvalidEntryTimeException extends RuntimeException {
    public InvalidEntryTimeException(){
        super();
    }
    public InvalidEntryTimeException(String msg){
     super(msg);
    }
}
