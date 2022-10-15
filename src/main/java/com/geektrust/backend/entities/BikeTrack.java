package com.geektrust.backend.entities;

import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;

public class BikeTrack extends RaceTrack{

    private static final Integer COST_PER_HOUR = 60;
    private static final Integer LIMIT = 4;
    private static final TrackType TRACK_TYPE = TrackType.REGULAR;
    private static final VehicleType VEHICLE_TYPE = VehicleType.BIKE;



    public BikeTrack() {
        super(TRACK_TYPE, COST_PER_HOUR, LIMIT,VEHICLE_TYPE);
    }
     //for future use (if bike also got VIP, then extend this class and call this constructor)
    BikeTrack(TrackType trackType, Integer costPerHour, Integer limit) {
        super(trackType, costPerHour, limit,VEHICLE_TYPE);
    }    
}
