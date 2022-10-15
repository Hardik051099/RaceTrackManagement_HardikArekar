package com.geektrust.backend.entities;

import java.time.LocalTime;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.utilities.Constants;

public class Race {
    //LocalTime time = LocalTime.parse("18:14:00");
    private final Vehicle vehicle;
    private final LocalTime entryTime;
    private LocalTime exitTime;
    private final String raceId;

    public Race(Vehicle vehicle , LocalTime entryTime){
        this.vehicle = vehicle;
        validateEntryTime(entryTime);
        this.entryTime = entryTime;
        this.exitTime = entryTime.plusHours(Constants.DEFAULT_DURATION_HOURS);
        this.raceId = vehicle.getVehicleNumber();
    }

    private void validateEntryTime(LocalTime raceEntryTime) throws InvalidEntryTimeException{
        if(raceEntryTime.isBefore(Constants.START_TIME) || raceEntryTime.isAfter(Constants.FINAL_BOOKING_TIME)){
            throw new InvalidEntryTimeException();
        }
    }
    private void validateExitTime(LocalTime raceExitTime) throws InvalidExitTimeException{
        if(raceExitTime.isAfter(Constants.END_TIME)){
            throw new InvalidExitTimeException();
        }
    }
    
    public String getRaceId() {
        return raceId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }
    public LocalTime setExitTime(LocalTime exitTime) {
        validateExitTime(exitTime);
        this.exitTime = exitTime;
        return exitTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entryTime == null) ? 0 : entryTime.hashCode());
        result = prime * result + ((raceId == null) ? 0 : raceId.hashCode());
        result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
        Race other = (Race) obj;
        if (entryTime == null) {
            if (other.entryTime != null)
                return false;
        } else if (!entryTime.equals(other.entryTime))
            return false;
        if (raceId == null) {
            if (other.raceId != null)
                return false;
        } else if (!raceId.equals(other.raceId))
            return false;
        if (vehicle == null) {
            if (other.vehicle != null)
                return false;
        } else if (!vehicle.equals(other.vehicle))
            return false;
        return true;
    }
   
}
