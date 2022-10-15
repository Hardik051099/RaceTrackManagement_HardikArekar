package com.geektrust.backend.exceptions;

public class InvalidRaceTrackException extends RuntimeException {
    public InvalidRaceTrackException(){
        super();
    }
    public InvalidRaceTrackException(String msg){
     super(msg);
    }
}
