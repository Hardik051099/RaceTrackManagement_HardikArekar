package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.exceptions.RaceNotFoundException;

public interface IRaceRepository extends CRUDRepository<Race>{
    public Optional<Race> getRaceByVehicleNumber(String vehicleNumber) throws RaceNotFoundException;
    
}
