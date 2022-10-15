package com.geektrust.backend.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.enums.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RaceTrack Test")
class RaceTrackTest {

    @Test
    @DisplayName("addNewRaceToTrack method Should add race")
    public void addNewRaceToTrack_shouldAddNewRaceToRaceTrack(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car = new Car("HA22");
        Race carRace = new Race(car, LocalTime.parse("14:00"));
        List<Race> raceList = new ArrayList<Race>(){{
            add(carRace);
        }}; 
        // Act
        carTrack.addNewRaceToTrack(carRace);
        // Assert
        Assertions.assertEquals(raceList, carTrack.getRaceList());
    }

    @Test
    @DisplayName("addNewRaceToTrack method Should return Failed Response when track is full or Time slot is not available")
    public void addNewRaceToTrack_shouldThrowException_RaceTrackFull(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle car3 = new Car("HA24");

        Race carRace = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race carRace3 = new Race(car3, LocalTime.parse("15:00"));
 
        // Act
        carTrack.addNewRaceToTrack(carRace);
        carTrack.addNewRaceToTrack(carRace2);
        // Assert
        Assertions.assertEquals(Response.FAILED ,carTrack.addNewRaceToTrack(carRace3));
    }

    @Test
    @DisplayName("EDGE CASE: addNewRaceToTrack method Should send Success response when time slot is available and Add races")
    public void addNewRaceToTrack_shouldNOTThrowException_TimeSlotAvailable(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle car3 = new Car("HA24");
        Vehicle car4 = new Car("HA26");


        Race carRace = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("17:00"));

        Race carRace3 = new Race(car3, LocalTime.parse("14:00"));
        Race carRace4 = new Race(car4, LocalTime.parse("17:00"));

        List<Race> raceList = new ArrayList<Race>(){{
            add(carRace);
            add(carRace2);
            add(carRace3);
            add(carRace4);
        }}; 
        // Act
        carTrack.addNewRaceToTrack(carRace);
        carTrack.addNewRaceToTrack(carRace2);
        carTrack.addNewRaceToTrack(carRace3);


        // Assert
        Assertions.assertEquals(Response.SUCCESS,carTrack.addNewRaceToTrack(carRace4));
        Assertions.assertEquals(raceList, carTrack.getRaceList());

    }

    @Test
    @DisplayName("updateRace Should update race and return SUCCESS response for updateRace is race is valid")
    public void updateRaceshouldReturnSuccess_IfRaceIsValid(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Race carRace = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        carTrack.addNewRaceToTrack(carRace);
        carTrack.addNewRaceToTrack(carRace2); 
        // Act
        carRace2.setExitTime(LocalTime.parse("18:00"));
        // Assert
        Assertions.assertEquals(Response.SUCCESS ,carTrack.updateRace(carRace2));
    }

    @Test
    @DisplayName("updateRace Should NOT update race and return FAILED response for updateRace is race is invalid")
    public void updateRaceshouldReturnFailed_IfRaceIsInvalidValid(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle car3 = new Car("HA24");

        Race carRace1 = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race carRace3 = new Race(car3, LocalTime.parse("17:00"));

        carTrack.addNewRaceToTrack(carRace1);
        carTrack.addNewRaceToTrack(carRace2); 
        carTrack.addNewRaceToTrack(carRace3);
        // Act
        carRace1.setExitTime(LocalTime.parse("18:00"));
        // Assert
        Assertions.assertEquals(Response.FAILED ,carTrack.updateRace(carRace1));
    }

    @Test
    @DisplayName("isRaceTrackFull Should return false if racetrack has space for new race")
    public void isRaceTrackFull_ShouldReturnFalseIfRaceTrackNotFull(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle car3 = new Car("HA24");

        Race carRace1 = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race carRace3 = new Race(car3, LocalTime.parse("17:00"));

        carTrack.addNewRaceToTrack(carRace1);
        carTrack.addNewRaceToTrack(carRace2); 
        // Act
        // Assert
        Assertions.assertFalse(carTrack.isRaceTrackFull(carRace3));
    }

    @Test
    @DisplayName("isRaceTrackFull Should return true if racetrack is full")
    public void isRaceTrackFull_ShouldReturnTrueIfRaceTrackFull(){
        // Arrange
        RaceTrack carTrack = new CarTrack();
        Vehicle car1 = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle car3 = new Car("HA24");
        Vehicle car4 = new Car("HA25");

        Race carRace1 = new Race(car1, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race carRace3 = new Race(car3, LocalTime.parse("17:00"));
        Race carRace4 = new Race(car4, LocalTime.parse("16:00"));

        carTrack.addNewRaceToTrack(carRace1);
        carTrack.addNewRaceToTrack(carRace2); 
        carTrack.addNewRaceToTrack(carRace3); 

        // Act
        // Assert
        Assertions.assertTrue(carTrack.isRaceTrackFull(carRace4));
    }

}
