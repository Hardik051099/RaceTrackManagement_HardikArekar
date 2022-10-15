package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;
import static com.geektrust.backend.utilities.Utils.generateID;

public class RaceTrackRepository implements IRaceTrackRepository {

    private final Map <String,RaceTrack> raceTrackMap;


    public RaceTrackRepository(){
        this.raceTrackMap = new HashMap<>();
    }
    public RaceTrackRepository(Map <String,RaceTrack> raceTrackMap){
        this.raceTrackMap = raceTrackMap;
    }

    @Override
    public RaceTrack save(RaceTrack entity) {
        String TrackID = generateID(entity.getTrackType(),entity.getVehicletype());
        raceTrackMap.put(TrackID, entity);
        return entity;
    }

    @Override
    public List<RaceTrack> fetchAll() {
        return raceTrackMap.values().stream().collect(Collectors.toList());
    }
    @Override
    public Optional<RaceTrack> getRaceTrackByRace(Race race) {

        return raceTrackMap.values()
        .stream()
        .filter(tracks -> tracks.getRaceList().contains(race))
        .findAny();
    }
    @Override
    public boolean isRaceTrackExists(VehicleType vehicleType , TrackType trackType) {
        return raceTrackMap.values()
        .stream()
        .filter(track ->
            track.getVehicletype().equals(vehicleType) && 
            track.getTrackType().equals(trackType)
        ).count() > 0;
    }
    @Override
    public Optional<RaceTrack> findById(String id) {
        return Optional.ofNullable(raceTrackMap.get(id));
    }
    @Override
    public void delete(RaceTrack entity) {
        String id = generateID(entity.getTrackType(),entity.getVehicletype());
        if(raceTrackMap.containsKey(id)){
            raceTrackMap.remove(id);        
        }
        else{
            throw new RaceTrackNotFoundException();
        }
    }
    

    

  
    
}
