package com.geektrust.backend.entities;

import com.geektrust.backend.enums.TrackType;

public class CarTrackVIP extends CarTrack{

    private static final Integer COST_PER_HOUR = 250;
    private static final Integer LIMIT = 1;
    private static final TrackType trackType = TrackType.VIP;

    public CarTrackVIP() {
        super(trackType, COST_PER_HOUR, LIMIT);
    }

}
