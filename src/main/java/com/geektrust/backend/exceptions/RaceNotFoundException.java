package com.geektrust.backend.exceptions;

public class RaceNotFoundException extends RuntimeException {
    public RaceNotFoundException(){
        super();
    }
    public RaceNotFoundException(String msg){
     super(msg);
    }
}
