package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.exceptions.RaceNotFoundException;

public class RaceRepository implements IRaceRepository {


    private final Map <String,Race> raceMap;

    public RaceRepository(){
        this.raceMap = new HashMap<>();
    }
    public RaceRepository(Map <String,Race> raceMap){
        this.raceMap = raceMap;
    }

    @Override
    public Race save(Race entity) {
        raceMap.put(entity.getRaceId(),entity);
        return entity;
    }

    @Override
    public List<Race> fetchAll() {
        return raceMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Race> getRaceByVehicleNumber(String vehicleNumber) throws RaceNotFoundException {

        return raceMap.values().stream().filter(race -> race.getVehicle().getVehicleNumber().equals(vehicleNumber)).findAny();
    }
    @Override
    public Optional<Race> findById(String id) {
        return Optional.ofNullable(raceMap.get(id));
    }
    @Override
    public void delete(Race entity) {
        String id = entity.getRaceId();
        if(raceMap.containsKey(id)){
            raceMap.remove(id);        
        }
        else{
            throw new RaceNotFoundException();
        }
    }
    
}
