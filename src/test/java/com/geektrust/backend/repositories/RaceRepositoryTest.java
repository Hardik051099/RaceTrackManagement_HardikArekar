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
import com.geektrust.backend.entities.Car;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.Suv;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.exceptions.RaceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Race Repository Test")
public class RaceRepositoryTest {
    private RaceRepository raceRepository;

    @BeforeEach
    void setup(){

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

        final Map<String,Race> raceMap = new HashMap<String,Race>(){
            {
                put(bikeRace1.getRaceId(),bikeRace1);
                put(bikeRace2.getRaceId(),bikeRace2);
                put(carRace1.getRaceId(),carRace1);
                put(carRace2.getRaceId(),carRace2);                
                put(vipRace.getRaceId(),vipRace);
                put(suvRace.getRaceId(),suvRace);                
                put(vipRace2.getRaceId(),vipRace2);
            }
        };
        raceRepository = new RaceRepository(raceMap);
    }

    @Test
    @DisplayName("save method should create or return existing race")
    public void saveRace(){
        //Arrange
        Vehicle suv1 = new Suv("HA25");
        Race expectedSuvRace = new Race(suv1, LocalTime.parse("15:00"));        
        //Act
        Race actualSuvRace = raceRepository.save(expectedSuvRace);
        //Assert
        Assertions.assertEquals(expectedSuvRace,actualSuvRace);
    }

    @Test
    @DisplayName("fetch All method should return all existing races")
    public void fetchAllShouldReturnAllRaces(){
        //Arrange

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

        final List<Race> expectedRacekList = new ArrayList<Race>(){
            {
                add(bikeRace1);
                add(bikeRace2);
                add(carRace1);
                add(carRace2);
                add(vipRace);
                add(suvRace);
                add(vipRace2);
            }
        };
        
        //Act
        List<Race> actualRaceList = raceRepository.fetchAll();
        Collections.sort(expectedRacekList,Comparator.comparing(Race::getRaceId));
        Collections.sort(actualRaceList,Comparator.comparing(Race::getRaceId));
        //Assert
        Assertions.assertEquals(expectedRacekList,actualRaceList);
    }

    @Test
    @DisplayName("getRaceByVehicleNumber should return correct race having vehicle")
    public void getRaceByVehicleNumber_ShouldReturnCorrectRace(){
        //Arrange
        Vehicle bike = new Bike("HA20");
        Race expectedRace = new Race(bike, LocalTime.parse("13:00"));        
        //Act
        Race actualRace = raceRepository.getRaceByVehicleNumber("HA20").get();
        //Assert
        Assertions.assertEquals(expectedRace,actualRace);
    }

    @Test
    @DisplayName("getRaceTrackByRace should return empty if race for vehicle number not found")
    public void getRaceByVehicleNumberReturnsEmpty_IfRaceNotFound(){
        //Arrange      
        //Act
        //Assert
        Assertions.assertEquals(Optional.empty(),raceRepository.getRaceByVehicleNumber("H124"));
    }

    @Test
    @DisplayName("findById should return correct race for valid vehicleNumber")
    public void findById_shouldReturnRace(){
        //Arrange 
        Vehicle car = new Car("HA22");
        Race expectedRace = new Race(car, LocalTime.parse("13:00"));
        
        //Act Assert
        Assertions.assertEquals(expectedRace,raceRepository.findById("HA22").get());
    }

    @Test
    @DisplayName("findById should return empty race for no race with given vehicle no")
    public void findById_shouldReturnEmpty_InvalidVehicleNo(){
        //Arrange 
        Vehicle car = new Car("HA22");
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        raceRepository.delete(carRace1);
        //Also covers delete test case
        //Act Assert
        Assertions.assertEquals(raceRepository.findById("HA22"),Optional.empty());
    }

    @Test
    @DisplayName("DeleteById should throw exception for not present entity")
    public void deleteById_ShouldThrowExceptionIfEntitynotFound(){
        //Arrange 
        Vehicle car = new Car("HA22");
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        raceRepository.delete(carRace1);
        //Act Assert 
        Assertions.assertThrows(RaceNotFoundException.class,() -> raceRepository.delete(carRace1));
    }
}
