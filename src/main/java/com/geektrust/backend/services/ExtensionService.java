package com.geektrust.backend.services;

import java.time.LocalTime;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.exceptions.RaceNotFoundException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;
import com.geektrust.backend.repositories.IRaceRepository;
import com.geektrust.backend.repositories.IRaceTrackRepository;

public class ExtensionService implements IExtensionService{
    
    private final IRaceTrackRepository raceTrackRepository;
    private final IRaceRepository raceRepository;

    public ExtensionService(IRaceTrackRepository raceTrackRepository,
            IRaceRepository raceRepository) {
        this.raceTrackRepository = raceTrackRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public RaceBookingDto extendRace(String vehicleNumber, LocalTime exitTime)
            throws RaceTrackFullException, InvalidExitTimeException,RaceNotFoundException,RaceTrackNotFoundException {
        final Race currentRace = raceRepository.getRaceByVehicleNumber(vehicleNumber).orElseThrow(() -> new RaceNotFoundException());
        final RaceTrack currentRaceTrack = raceTrackRepository.getRaceTrackByRace(currentRace).orElseThrow(() -> new RaceTrackNotFoundException()); 
        
        try{
            currentRace.setExitTime(exitTime);
        }
        catch (InvalidExitTimeException e){
            throw new InvalidExitTimeException();
        }

        Response extensionResponse = currentRaceTrack.updateRace(currentRace);

        if(extensionResponse.equals(Response.FAILED)){
            throw new RaceTrackFullException();
        }
        raceRepository.save(currentRace);
        raceTrackRepository.save(currentRaceTrack);
        
        return new RaceBookingDto(currentRace, currentRaceTrack);
    }





}
