package com.geektrust.backend.repositories;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RaceTrack Repository Test")
public class RaceTrackRepositoryTest {
    
    private RaceTrackRepository raceTrackRepository;

    @BeforeEach
    void setup(){

        RaceTrack bikeTrack = new BikeTrack();
        RaceTrack carTrack = new CarTrack();
        RaceTrack carTrackVIP = new CarTrackVIP();
        RaceTrack suvTrack = new SuvTrack();
        RaceTrack suvTrackVIP = new SuvTrackVIP();

        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA21");
        Vehicle car = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle vipCar = new Car("HA24");
        Vehicle suv1 = new Suv("HA25");
        Vehicle vipSuv = new Suv("HA26");

        Race bikeRace1 = new Race(bike, LocalTime.parse("13:00"));
        Race bikeRace2 = new Race(bike2, LocalTime.parse("14:00"));
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race vipRace = new Race(vipCar, LocalTime.parse("14:00"));
        Race suvRace = new Race(suv1, LocalTime.parse("15:00"));
        Race vipRace2 = new Race(vipSuv, LocalTime.parse("13:15"));

        bikeTrack.addNewRaceToTrack(bikeRace1);
        bikeTrack.addNewRaceToTrack(bikeRace2);
        carTrack.addNewRaceToTrack(carRace1);
        carTrack.addNewRaceToTrack(carRace2);
        carTrackVIP.addNewRaceToTrack(vipRace);
        suvTrack.addNewRaceToTrack(suvRace);
        suvTrackVIP.addNewRaceToTrack(vipRace2);

        final Map<String,RaceTrack> raceTrackMap = new HashMap<String,RaceTrack>(){
            {
                put(bikeTrack.getTrackType()+"_"+bikeTrack.getVehicletype(),bikeTrack);
                put(carTrack.getTrackType()+"_"+carTrack.getVehicletype(),carTrack);
                put(carTrackVIP.getTrackType()+"_"+carTrackVIP.getVehicletype(),carTrackVIP);
                put(suvTrack.getTrackType()+"_"+suvTrack.getVehicletype(),suvTrack);
                put(suvTrackVIP.getTrackType()+"_"+suvTrackVIP.getVehicletype(),suvTrackVIP);
            }
        };
        raceTrackRepository = new RaceTrackRepository(raceTrackMap);
    }

    @Test
    @DisplayName("save method should create or return existing RaceTrack")
    public void saveRaceTrack(){
        //Arrange
        final SuvTrack suvTrack = new SuvTrack();
        Vehicle suv1 = new Suv("HA25");
        Race suvRace = new Race(suv1, LocalTime.parse("15:00"));
        suvTrack.addNewRaceToTrack(suvRace);
        
        //Act
        RaceTrack actual = raceTrackRepository.save(suvTrack);
        //Assert
        Assertions.assertEquals(suvTrack,actual);
    }

    @Test
    @DisplayName("fetch All method should return all existing tracks")
    public void fetchAllShouldReturnAllRaceTracks(){
        //Arrange
        RaceTrack bikeTrack = new BikeTrack();
        RaceTrack carTrack = new CarTrack();
        RaceTrack carTrackVIP = new CarTrackVIP();
        RaceTrack suvTrack = new SuvTrack();
        RaceTrack suvTrackVIP = new SuvTrackVIP();

        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA21");
        Vehicle car = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle vipCar = new Car("HA24");
        Vehicle suv1 = new Suv("HA25");
        Vehicle vipSuv = new Suv("HA26");

        Race bikeRace1 = new Race(bike, LocalTime.parse("13:00"));
        Race bikeRace2 = new Race(bike2, LocalTime.parse("14:00"));
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race vipRace = new Race(vipCar, LocalTime.parse("14:00"));
        Race suvRace = new Race(suv1, LocalTime.parse("15:00"));
        Race vipRace2 = new Race(vipSuv, LocalTime.parse("13:15"));

        bikeTrack.addNewRaceToTrack(bikeRace1);
        bikeTrack.addNewRaceToTrack(bikeRace2);
        carTrack.addNewRaceToTrack(carRace1);
        carTrack.addNewRaceToTrack(carRace2);
        carTrackVIP.addNewRaceToTrack(vipRace);
        suvTrack.addNewRaceToTrack(suvRace);
        suvTrackVIP.addNewRaceToTrack(vipRace2);

        final List<RaceTrack> expectedRaceTrackList = new ArrayList<RaceTrack>(){
            {
                add(bikeTrack);
                add(carTrack);
                add(carTrackVIP);
                add(suvTrack);
                add(suvTrackVIP);
            }
        };
        
        //Act
        List<RaceTrack> actualRaceTrackList = raceTrackRepository.fetchAll();
        Collections.sort(expectedRaceTrackList,Comparator.comparing(RaceTrack::getTrackType));
        Collections.sort(actualRaceTrackList,Comparator.comparing(RaceTrack::getTrackType));
        //Assert
        Assertions.assertEquals(expectedRaceTrackList,actualRaceTrackList);
    }

    @Test
    @DisplayName("getRaceTrackByRace should return correct racetrack binded to given race")
    public void getRaceTrackByRaceReturnsCorrectRaceTrack(){
        //Arrange
        final SuvTrack expectedTrack = new SuvTrack();
        Vehicle suv1 = new Suv("HA25");
        Race suvRace = new Race(suv1, LocalTime.parse("15:00"));
        expectedTrack.addNewRaceToTrack(suvRace);
        
        //Act
        RaceTrack actualRaceTrack = raceTrackRepository.getRaceTrackByRace(suvRace).get();
        //Assert
        Assertions.assertEquals(expectedTrack,actualRaceTrack);
    }

    @Test
    @DisplayName("getRaceTrackByRace should return empty if racetrack for race not found")
    public void getRaceTrackByRaceReturnsEmpty_IfRaceTrackNotFound(){
        //Arrange
        final SuvTrack expectedTrack = new SuvTrack();
        Vehicle suv1 = new Suv("HA2325");
        Race suvRace = new Race(suv1, LocalTime.parse("16:00"));
        expectedTrack.addNewRaceToTrack(suvRace);
        
        //Act
        //Assert
        Assertions.assertEquals(Optional.empty(),raceTrackRepository.getRaceTrackByRace(suvRace));
    }

    @Test
    @DisplayName("isRaceTrackExists should return true for existing track")
    public void isRaceTrackExistsReturnTrueFor_ExistingTrack(){
        //Arrange Act Assert
        Assertions.assertTrue(raceTrackRepository.isRaceTrackExists(VehicleType.CAR, TrackType.VIP));
    }

    @Test
    @DisplayName("isRaceTrackExists should return false for non existing track")
    public void isRaceTrackExistsReturnFalseFor_NonExistingTrack(){
        //Arrange Act Assert 
        //it also covers Delete test case
        raceTrackRepository.delete(new CarTrack());
        Assertions.assertFalse(raceTrackRepository.isRaceTrackExists(VehicleType.CAR, TrackType.REGULAR));
    }

    @Test
    @DisplayName("findById should return correct raceTrack for valid ID")
    public void findById_shouldReturnRaceTrack(){
        //Arrange Act Assert
        Assertions.assertEquals(raceTrackRepository.findById("REGULAR_CAR").get(),new CarTrack());
    }

    @Test
    @DisplayName("findById should return empty raceTrack for Invalid ID")
    public void findById_shouldReturnEmpty_InvalidId(){
        //Arrange Act Assert
        Assertions.assertEquals(raceTrackRepository.findById("REGULAR_CAR_ABC"),Optional.empty());
    }

    @Test
    @DisplayName("Deleteshould throw exception for not present entity")
    public void delete_ShouldThrowExceptionIfEntitynotFound(){
        //Arrange Act Assert 
        raceTrackRepository.delete(new CarTrack());
        Assertions.assertThrows(RaceTrackNotFoundException.class,() -> raceTrackRepository.delete(new CarTrack()));
    }

}
