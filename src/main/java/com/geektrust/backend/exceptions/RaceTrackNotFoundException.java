package com.geektrust.backend.exceptions;

public class RaceTrackNotFoundException extends RuntimeException {
    public RaceTrackNotFoundException(){
        super();
    }
    public RaceTrackNotFoundException(String msg){
     super(msg);
    }
}
