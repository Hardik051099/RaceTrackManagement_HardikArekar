package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;

public interface IRaceTrackRepository extends CRUDRepository<RaceTrack> {
    public Optional<RaceTrack> getRaceTrackByRace(Race race) throws RaceTrackNotFoundException;
    public boolean isRaceTrackExists(VehicleType vehicleType , TrackType trackType) ;
}
