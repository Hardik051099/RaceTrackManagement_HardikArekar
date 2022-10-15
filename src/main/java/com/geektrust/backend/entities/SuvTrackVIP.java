package com.geektrust.backend.entities;

import com.geektrust.backend.enums.TrackType;

public class SuvTrackVIP extends SuvTrack{

    private static final Integer COST_PER_HOUR = 300;
    private static final Integer LIMIT = 1;
    private static final TrackType trackType = TrackType.VIP;

    public SuvTrackVIP() {
        super(trackType, COST_PER_HOUR, LIMIT);
    }

}
