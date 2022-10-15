package com.geektrust.backend.services;

import java.time.LocalTime;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.entities.Bike;
import com.geektrust.backend.entities.BikeTrack;
import com.geektrust.backend.entities.Car;
import com.geektrust.backend.entities.CarTrack;
import com.geektrust.backend.entities.CarTrackVIP;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.entities.Suv;
import com.geektrust.backend.entities.SuvTrack;
import com.geektrust.backend.entities.SuvTrackVIP;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.repositories.IRaceRepository;
import com.geektrust.backend.repositories.IRaceTrackRepository;
import static com.geektrust.backend.utilities.Utils.generateID;

public class BookingService implements IBookingService {

    private final IRaceTrackRepository raceTrackRepository;
    private final IRaceRepository raceRepository;

    public BookingService (IRaceTrackRepository raceTrackRepository , IRaceRepository raceRepository){
        this.raceTrackRepository = raceTrackRepository;
        this.raceRepository = raceRepository;
    }



    @Override
    public RaceBookingDto bookRace(VehicleType vehicleType, String vehicleNumber, LocalTime entryTime)
            throws RaceTrackFullException, InvalidEntryTimeException {

        final Vehicle vehicle = createVehicle(vehicleType,vehicleNumber);
        final Race race;
        
        try {
            race = new Race(vehicle, entryTime);
        } catch (InvalidEntryTimeException e){
            throw new InvalidEntryTimeException();
        }

        RaceTrack raceTrack;
        raceTrack = getRaceTrack(vehicleType);
        Response response = raceTrack.addNewRaceToTrack(race);
        //checking response , if failed and not bike then try vip track
        if(response.equals(Response.FAILED) && !(vehicleType.equals(VehicleType.BIKE))){
            raceTrack = getRaceTrackVIP(vehicleType);
            response = raceTrack.addNewRaceToTrack(race);
        }
        //Else-if will not work here since we have to check the response again in the case of VIP , so used 2 Ifs
        if(response.equals(Response.SUCCESS)){
            raceRepository.save(race);
            raceTrackRepository.save(raceTrack);
        }
        else {
            throw new RaceTrackFullException();
        }
        return new RaceBookingDto(race, raceTrack);
    }

    private Vehicle createVehicle(VehicleType vehicleType , String vehicleNumber){
        switch(vehicleType){
            case BIKE:
                return new Bike(vehicleNumber);
            case CAR:
                return new Car(vehicleNumber);
            case SUV:
                return new Suv(vehicleNumber);
            default:
                return new Vehicle(vehicleNumber, vehicleType);
        }
    }
    private RaceTrack getRaceTrack(VehicleType vehicleType){
        final TrackType trackType = TrackType.REGULAR;
        if(raceTrackRepository.isRaceTrackExists(vehicleType, trackType)){
            return raceTrackRepository.findById(generateID(trackType, vehicleType)).get();
        }
        else {
            switch(vehicleType){
                case CAR:
                    return raceTrackRepository.save(new CarTrack());
                case BIKE:
                    return raceTrackRepository.save(new BikeTrack());
                case SUV:
                    return raceTrackRepository.save(new SuvTrack());
                default:
                    return new RaceTrack();
            }
        }
    }
    private RaceTrack getRaceTrackVIP(VehicleType vehicleType){
        final TrackType trackType = TrackType.VIP;
        if(raceTrackRepository.isRaceTrackExists(vehicleType, trackType)){
            return raceTrackRepository.findById(generateID(trackType, vehicleType)).get();
        }
        else {
            switch(vehicleType){
                case CAR:
                    return raceTrackRepository.save(new CarTrackVIP());
                case SUV:
                    return raceTrackRepository.save(new SuvTrackVIP());
                default:
                    return new RaceTrack();
            }
        }
    }



    
}
