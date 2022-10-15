package com.geektrust.backend.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.dtos.TotalRevenueDto;
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
import com.geektrust.backend.repositories.IRaceTrackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Revenue Service Test")
@ExtendWith(MockitoExtension.class)
public class RevenueServiceTest {
    @Mock
    private IRaceTrackRepository raceTrackRepositoryMock;

    @InjectMocks
    private RevenueService revenueService;
    
    @Test
    @DisplayName("calculateRevenue method Should calculate proper revenue")
    public void calculateRevenue_shouldCalculateProperRevenue() throws Exception{
        // Arrange
        RaceTrack raceTrack1 = new BikeTrack();
        RaceTrack raceTrack2 = new CarTrack();
        RaceTrack raceTrack3 = new CarTrackVIP();

        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA21");
        Vehicle car = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle vipCar = new Car("HA24");

        Race bikeRace1 = new Race(bike, LocalTime.parse("13:00"));
        Race bikeRace2 = new Race(bike2, LocalTime.parse("14:00"));
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race vipRace = new Race(vipCar, LocalTime.parse("14:00"));

        raceTrack1.addNewRaceToTrack(bikeRace1);
        raceTrack1.addNewRaceToTrack(bikeRace2);
        raceTrack2.addNewRaceToTrack(carRace1);
        raceTrack2.addNewRaceToTrack(carRace2);
        raceTrack3.addNewRaceToTrack(vipRace);

        List<RaceTrack> raceTrackList = new ArrayList<RaceTrack>(){{
            add(raceTrack1);
            add(raceTrack2);
            add(raceTrack3);
        }};

        when(raceTrackRepositoryMock.fetchAll()).thenReturn(raceTrackList);

        // Act
        TotalRevenueDto actual = revenueService.calculateTotalRevenue();
        // Assert
        Assertions.assertEquals(new TotalRevenueDto(1080, 750), actual);
        verify(raceTrackRepositoryMock,times(1)).fetchAll();

    
    }
    @Test
    @DisplayName("calculateRevenue method Should calculate proper revenue when time extension is within Free duration, Extra <= 15min")
    public void calculateRevenue_shouldCalculateProperRevenue_ForFreeExtraDuration() throws Exception{
        // Arrange
        RaceTrack raceTrack1 = new BikeTrack();
        RaceTrack raceTrack2 = new CarTrack();
        RaceTrack raceTrack3 = new CarTrackVIP();
        RaceTrack raceTrack4 = new SuvTrack();


        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA21");
        Vehicle car = new Car("HA22");
        Vehicle car2 = new Car("HA23");
        Vehicle vipCar = new Car("HA24");
        Vehicle suv1 = new Suv("HA25");


        Race bikeRace1 = new Race(bike, LocalTime.parse("13:00"));
        Race bikeRace2 = new Race(bike2, LocalTime.parse("14:00"));
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        Race carRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race vipRace = new Race(vipCar, LocalTime.parse("14:00"));
        Race suvRace = new Race(suv1, LocalTime.parse("15:00"));

        suvRace.setExitTime(LocalTime.parse("18:15"));

        raceTrack1.addNewRaceToTrack(bikeRace1);
        raceTrack1.addNewRaceToTrack(bikeRace2);
        raceTrack2.addNewRaceToTrack(carRace1);
        raceTrack2.addNewRaceToTrack(carRace2);
        raceTrack3.addNewRaceToTrack(vipRace);
        raceTrack4.addNewRaceToTrack(suvRace);


        List<RaceTrack> raceTrackList = new ArrayList<RaceTrack>(){{
            add(raceTrack1);
            add(raceTrack2);
            add(raceTrack3);
            add(raceTrack4);
        }};

        when(raceTrackRepositoryMock.fetchAll()).thenReturn(raceTrackList);

        // Act
        TotalRevenueDto actual = revenueService.calculateTotalRevenue();
        // Assert
        Assertions.assertEquals(new TotalRevenueDto(1680, 750), actual);
        verify(raceTrackRepositoryMock,times(1)).fetchAll();

    }

    @Test
    @DisplayName("calculateRevenue method Should calculate proper revenue with Extra charge if Extra > 15min")
    public void calculateRevenue_shouldCalculateProperRevenue_WithExtraCharge() throws Exception{
        // Arrange
        RaceTrack raceTrack1 = new BikeTrack();
        RaceTrack raceTrack2 = new CarTrack();
        RaceTrack raceTrack3 = new CarTrackVIP();
        RaceTrack raceTrack4 = new SuvTrack();
        RaceTrack raceTrack5 = new SuvTrackVIP();


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

        suvRace.setExitTime(LocalTime.parse("18:16")); //16 min extra --> 1 hour extra charge
        vipRace2.setExitTime(LocalTime.parse("17:16")); //1 hr 1 min extra --> 2 hours extra charge
        carRace1.setExitTime(LocalTime.parse("18:00"));//2 hrs extra --> 2 hours extra charge

        raceTrack1.addNewRaceToTrack(bikeRace1);
        raceTrack1.addNewRaceToTrack(bikeRace2);
        raceTrack2.addNewRaceToTrack(carRace1);
        raceTrack2.addNewRaceToTrack(carRace2);
        raceTrack3.addNewRaceToTrack(vipRace);
        raceTrack4.addNewRaceToTrack(suvRace);
        raceTrack5.addNewRaceToTrack(vipRace2);


        List<RaceTrack> raceTrackList = new ArrayList<RaceTrack>(){{
            add(raceTrack1);
            add(raceTrack2);
            add(raceTrack3);
            add(raceTrack4);
            add(raceTrack5);
        }};

        when(raceTrackRepositoryMock.fetchAll()).thenReturn(raceTrackList);

        // Act
        TotalRevenueDto actual = revenueService.calculateTotalRevenue();
        // Assert
        Assertions.assertEquals(new TotalRevenueDto(1830, 1750), actual);
        verify(raceTrackRepositoryMock,times(1)).fetchAll();

    }
}
