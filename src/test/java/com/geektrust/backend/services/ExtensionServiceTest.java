package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalTime;
import java.util.Optional;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.entities.Bike;
import com.geektrust.backend.entities.BikeTrack;
import com.geektrust.backend.entities.Car;
import com.geektrust.backend.entities.CarTrack;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.repositories.IRaceRepository;
import com.geektrust.backend.repositories.IRaceTrackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Extension Service Test")
@ExtendWith(MockitoExtension.class)
public class ExtensionServiceTest {
    @Mock
    private IRaceTrackRepository raceTrackRepositoryMock;

    @Mock
    private IRaceRepository raceRepositoryMock;

    @InjectMocks
    private ExtensionService extensionService;

    @Test
    @DisplayName("extendRace method Should extend race for valid time")
    public void extendRace_shouldExtendRace_ForValidTime() throws Exception{
        // Arrange
        RaceTrack raceTrack = new BikeTrack();
        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA25");
        Race demoRace = new Race(bike, LocalTime.parse("13:00"));
        Race extensionRace = new Race(bike2, LocalTime.parse("14:00"));
        raceTrack.addNewRaceToTrack(demoRace);
        raceTrack.addNewRaceToTrack(extensionRace);

        Race expectedRace = new Race(bike2, LocalTime.parse("14:00"));
        expectedRace.setExitTime(LocalTime.parse("18:00"));
        RaceTrack expectedRaceTrack = new BikeTrack();
        expectedRaceTrack.addNewRaceToTrack(demoRace);
        expectedRaceTrack.addNewRaceToTrack(expectedRace);

        RaceBookingDto expected = new RaceBookingDto(expectedRace, expectedRaceTrack);

        when(raceRepositoryMock.getRaceByVehicleNumber(anyString())).thenReturn(Optional.of(extensionRace));
        when(raceTrackRepositoryMock.getRaceTrackByRace(any(Race.class))).thenReturn(Optional.of(raceTrack));

        // Act
        RaceBookingDto actual = extensionService.extendRace("HA25", LocalTime.parse("18:00"));
        // Assert
        Assertions.assertEquals(expected, actual);
        verify(raceRepositoryMock,times(1)).getRaceByVehicleNumber(anyString());
        verify(raceTrackRepositoryMock,times(1)).getRaceTrackByRace(any(Race.class));

    }

    @Test
    @DisplayName("extendRace method Should throw RaceTrack exception if racetrack is not available")
    public void extendRace_shouldThrowException_ForFullRaceTrack() throws Exception{
        // Arrange
        RaceTrack raceTrack = new CarTrack();
        Vehicle car = new Car("HA10");
        Vehicle car2 = new Car("HA11");
        Vehicle car3 = new Car("HA12");
        Race demoRace = new Race(car, LocalTime.parse("13:00"));
        Race demoRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race demoRace3 = new Race(car3, LocalTime.parse("17:00"));

        raceTrack.addNewRaceToTrack(demoRace);
        raceTrack.addNewRaceToTrack(demoRace2);
        raceTrack.addNewRaceToTrack(demoRace3);

        when(raceRepositoryMock.getRaceByVehicleNumber(anyString())).thenReturn(Optional.of(demoRace2));
        when(raceTrackRepositoryMock.getRaceTrackByRace(any(Race.class))).thenReturn(Optional.of(raceTrack));

        //Act and Assert
        Assertions.assertThrows(RaceTrackFullException.class,() -> extensionService.extendRace("HA11", LocalTime.parse("18:00")));
        verify(raceRepositoryMock,times(1)).getRaceByVehicleNumber(anyString());
        verify(raceTrackRepositoryMock,times(1)).getRaceTrackByRace(any(Race.class));
        
    }

    @Test
    @DisplayName("extendRace method Should throw InvalidExitTimeException For invalid exit time")
    public void extendRace_shouldThrowException_ForInvalidExitTime() throws Exception{
        // Arrange
        RaceTrack raceTrack = new CarTrack();
        Vehicle car = new Car("HA10");
        Vehicle car2 = new Car("HA11");

        Race demoRace = new Race(car, LocalTime.parse("13:00"));
        Race demoRace2 = new Race(car2, LocalTime.parse("14:00"));

        raceTrack.addNewRaceToTrack(demoRace);
        raceTrack.addNewRaceToTrack(demoRace2);

        when(raceRepositoryMock.getRaceByVehicleNumber(anyString())).thenReturn(Optional.of(demoRace2));
        when(raceTrackRepositoryMock.getRaceTrackByRace(any(Race.class))).thenReturn(Optional.of(raceTrack));

        //Act and Assert
        Assertions.assertThrows(InvalidExitTimeException.class,() -> extensionService.extendRace("HA11", LocalTime.parse("21:00")));
        verify(raceRepositoryMock,times(1)).getRaceByVehicleNumber(anyString());
        verify(raceTrackRepositoryMock,times(1)).getRaceTrackByRace(any(Race.class));
        
    }
}
