package com.geektrust.backend.exceptions;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(){
        super();
    }
    public CommandNotFoundException(String msg){
     super(msg);
    }
}
