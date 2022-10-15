package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;

public class RaceBookingDto {
    private final Race race;
    private final RaceTrack raceTrack;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((race == null) ? 0 : race.hashCode());
        result = prime * result + ((raceTrack == null) ? 0 : raceTrack.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RaceBookingDto other = (RaceBookingDto) obj;
        if (race == null) {
            if (other.race != null)
                return false;
        } else if (!race.equals(other.race))
            return false;
        if (raceTrack == null) {
            if (other.raceTrack != null)
                return false;
        } else if (!raceTrack.equals(other.raceTrack))
            return false;
        return true;
    }

    public RaceBookingDto(Race race, RaceTrack raceTrack) {
        this.race = race;
        this.raceTrack = raceTrack;
    }

    public Race getRace() {
        return race;
    }

    public RaceTrack getRaceTrack() {
        return raceTrack;
    }    
}
