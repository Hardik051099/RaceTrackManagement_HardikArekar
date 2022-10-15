package com.geektrust.backend.entities;

import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;

public class CarTrack extends RaceTrack{

    private static final Integer COST_PER_HOUR = 120;
    private static final Integer LIMIT = 2;
    private static final TrackType TRACK_TYPE = TrackType.REGULAR;
    private static final VehicleType VEHICLE_TYPE = VehicleType.CAR;


    public CarTrack() {
        super(TRACK_TYPE, COST_PER_HOUR, LIMIT,VEHICLE_TYPE);
    }
    //this constructor will be called by VIP child object
    CarTrack(TrackType trackType, Integer costPerHour, Integer limit) {
        super(trackType, costPerHour, limit, VEHICLE_TYPE);
    }      
}
