package com.geektrust.backend.exceptions;

public class RaceTrackFullException extends RuntimeException {
    public RaceTrackFullException(){
        super();
    }
    public RaceTrackFullException(String msg){
     super(msg);
    }
}
