package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.InvalidRaceTrackException;
import com.geektrust.backend.utilities.Utils;

public class RaceTrack {
    private final TrackType trackType;
    private final Integer costPerHour;
    private final Integer limit;
    private final VehicleType vehicleType;
    protected List<Race> raceList;
    private final Integer SINGLE_VARIABLE = 1;

    RaceTrack(TrackType trackType,Integer costPerHour,Integer limit,VehicleType vehicleType){
        this.trackType = trackType;
        this.costPerHour = costPerHour;
        this.limit = limit;
        this.vehicleType = vehicleType;
        raceList = new ArrayList<>();
    }

    public RaceTrack() {
        this.trackType = null;
        this.costPerHour = 0;
        this.limit = 0;
        this.vehicleType = null;
        raceList = new ArrayList<>();
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public Integer getCostPerHour() {
        return costPerHour;
    }

    public Integer getLimit() {
        return limit;
    }
    
    public List<Race> getRaceList() {
        return raceList;
    }
    public VehicleType getVehicletype() {
        return vehicleType;
    }
    public Response updateRace(Race race){
        String raceId = race.getRaceId();
        //fetch existing Race to replace
        Race currentRace = raceList.stream()
                                    .filter(r -> raceId.equals(r.getRaceId()))
                                    .findAny()
                                    .orElse(null);
        //remove existing one
        raceList.remove(currentRace);
        Response newRaceResponse = addNewRaceToTrack(race);
        if(newRaceResponse.equals(Response.FAILED)){
            // if new updation failed then add the current race back to the list
            addNewRaceToTrack(currentRace);
            return Response.FAILED;
        }
        return Response.SUCCESS;
    }
    public Response addNewRaceToTrack(Race race){
        if(!(race.getVehicle().getVehicleType().equals(vehicleType))){
            throw new InvalidRaceTrackException();
        }

        if(isRaceTrackFull(race)){
            return Response.FAILED;
        }
        raceList.add(race);
        return Response.SUCCESS;
    }
    private int numberOfOverlaps (Race race){
        int[] counter = new int[SINGLE_VARIABLE];
        raceList.stream()
        .forEach(r -> {
            if((Utils.isOverlapping(race.getEntryTime(), race.getExitTime(), r.getEntryTime(), r.getExitTime()))){
                counter[0]++;
            }
        });
        return counter[0];
    }

    public Boolean isRaceTrackFull(Race race){
        if(numberOfOverlaps(race) >= limit){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((trackType == null) ? 0 : trackType.hashCode());
        result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
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
        RaceTrack other = (RaceTrack) obj;
        if (trackType != other.trackType)
            return false;
        if (vehicleType != other.vehicleType)
            return false;
        return true;
    }
    
    //1pm - 4pm 
    //5pm - 8pm

    //2pm - 5pm


}
