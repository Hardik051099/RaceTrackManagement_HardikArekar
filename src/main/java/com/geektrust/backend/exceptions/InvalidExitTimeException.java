package com.geektrust.backend.exceptions;

public class InvalidExitTimeException extends RuntimeException {
    public InvalidExitTimeException(){
        super();
    }
    public InvalidExitTimeException(String msg){
     super(msg);
    }
}
