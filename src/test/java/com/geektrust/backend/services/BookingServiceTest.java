package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
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
import com.geektrust.backend.entities.CarTrackVIP;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
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

@DisplayName("Booking Service Test")
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private IRaceTrackRepository raceTrackRepositoryMock;

    @Mock
    private IRaceRepository raceRepositoryMock;

    @InjectMocks
    private BookingService bookingService;

    @Test
    @DisplayName("bookRace method Should book valid race for new track")
    public void bookRace_shouldBookValidRace_ForNewTrack(){
        // Arrange
        Vehicle car = new Car("HA22");
        Race expectedRace = new Race(car, LocalTime.parse("14:00"));
        RaceTrack expectedRaceTrack = new CarTrack();        

        RaceBookingDto expected = new RaceBookingDto(expectedRace, expectedRaceTrack);

        when(raceTrackRepositoryMock.isRaceTrackExists(any(), any())).thenReturn(false);
        when(raceTrackRepositoryMock.save(any(RaceTrack.class))).thenReturn(expectedRaceTrack);
        when(raceRepositoryMock.save(any(Race.class))).thenReturn(expectedRace);

        // Act
        RaceBookingDto actual = bookingService.bookRace(VehicleType.CAR, "HA22", LocalTime.parse("14:00"));
        // Assert
        Assertions.assertEquals(expected, actual);
        verify(raceTrackRepositoryMock,times(1)).isRaceTrackExists(any(), any());
        verify(raceTrackRepositoryMock,times(2)).save(any(RaceTrack.class));
        verify(raceRepositoryMock,times(1)).save(any(Race.class));
    }

    @Test
    @DisplayName("bookRace method Should book valid race for existing track")
    public void bookRace_shouldBookValidRace_ForExistingTrack(){
        // Arrange
        RaceTrack raceTrack = new BikeTrack();

        Vehicle bike = new Bike("HA20");
        Vehicle bike2 = new Bike("HA25");

        Race demoRace = new Race(bike, LocalTime.parse("13:00"));
        Race expectedRace = new Race(bike2, LocalTime.parse("14:00"));
        raceTrack.addNewRaceToTrack(demoRace);
        
        RaceTrack expectedRaceTrack = new BikeTrack();
        expectedRaceTrack.addNewRaceToTrack(demoRace);
        expectedRaceTrack.addNewRaceToTrack(expectedRace);

        RaceBookingDto expected = new RaceBookingDto(expectedRace, expectedRaceTrack);

        when(raceTrackRepositoryMock.isRaceTrackExists(any(), any())).thenReturn(true);
        when(raceTrackRepositoryMock.findById(any())).thenReturn(Optional.of(raceTrack));
        when(raceTrackRepositoryMock.save(any(RaceTrack.class))).thenReturn(expectedRaceTrack);
        when(raceRepositoryMock.save(any(Race.class))).thenReturn(expectedRace);

        // Act
        RaceBookingDto actual = bookingService.bookRace(VehicleType.BIKE, "HA25", LocalTime.parse("14:00"));
        // Assert
       Assertions.assertEquals(expected, actual);
        verify(raceTrackRepositoryMock,times(1)).isRaceTrackExists(any(), any());
        verify(raceTrackRepositoryMock,times(1)).findById(any());
        verify(raceTrackRepositoryMock,times(1)).save(any(RaceTrack.class));
        verify(raceRepositoryMock,times(1)).save(any(Race.class));
    }

    @Test
    @DisplayName("bookRace method Should book race for VIP track if regular is full")
    public void bookRace_shouldBookRace_ForVIPIfRegularFull(){
        // Arrange
        RaceTrack raceTrack = new CarTrack();

        Vehicle car = new Car("HA1");
        Vehicle car2 = new Car("HA2");
        Vehicle car3 = new Car("HA3");

        Race demoRace1 = new Race(car, LocalTime.parse("13:00"));
        Race demoRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race expectedRace = new Race(car3, LocalTime.parse("14:00"));
        RaceTrack expectedRaceTrack = new CarTrackVIP();

        raceTrack.addNewRaceToTrack(demoRace1);
        raceTrack.addNewRaceToTrack(demoRace2);

        RaceBookingDto expected = new RaceBookingDto(expectedRace, expectedRaceTrack);

        when(raceTrackRepositoryMock.isRaceTrackExists(any(), any())).thenReturn(true,false);
        when(raceTrackRepositoryMock.findById(any())).thenReturn(Optional.of(raceTrack));
        when(raceTrackRepositoryMock.save(any(RaceTrack.class))).thenReturn(expectedRaceTrack);
        when(raceRepositoryMock.save(any(Race.class))).thenReturn(expectedRace);

        // Act
        RaceBookingDto actual = bookingService.bookRace(VehicleType.CAR, "HA3", LocalTime.parse("14:00"));

        // Assert
        Assertions.assertEquals(expected, actual);
        verify(raceTrackRepositoryMock,times(2)).isRaceTrackExists(any(), any());
        verify(raceTrackRepositoryMock,times(1)).findById(any());
        verify(raceTrackRepositoryMock,times(2)).save(any(RaceTrack.class));
        verify(raceRepositoryMock,times(1)).save(any(Race.class));
    }

    @Test
    @DisplayName("bookRace will throw RaceTrackFull Exception if both tracks full")
    @SuppressWarnings("unchecked")
    public void bookRace_shouldThrowExceptions_IfBothTracksFull(){
        // Arrange
        RaceTrack raceTrack = new CarTrack();

        Vehicle car = new Car("HA1");
        Vehicle car2 = new Car("HA2");
        Vehicle car3 = new Car("HA3");

        Race demoRace1 = new Race(car, LocalTime.parse("13:00"));
        Race demoRace2 = new Race(car2, LocalTime.parse("14:00"));
        Race demoRace3 = new Race(car3, LocalTime.parse("14:00"));

        RaceTrack vipRaceTrack = new CarTrackVIP();

        raceTrack.addNewRaceToTrack(demoRace1);
        raceTrack.addNewRaceToTrack(demoRace2);
        vipRaceTrack.addNewRaceToTrack(demoRace3);

        when(raceTrackRepositoryMock.isRaceTrackExists(any(), any())).thenReturn(true,true);      
        when(raceTrackRepositoryMock.findById(any())).thenReturn(Optional.of(raceTrack),Optional.of(vipRaceTrack));

        // Act and Assert
        Assertions.assertThrows(RaceTrackFullException.class, () -> bookingService.bookRace(VehicleType.CAR, "HA4", LocalTime.parse("14:00")));
        verify(raceTrackRepositoryMock,times(2)).isRaceTrackExists(any(), any());
        verify(raceTrackRepositoryMock,times(2)).findById(any());

    }

    @Test
    @DisplayName("bookRace will throw InvalidEntryTime Exception if Entry time is invalid")
    public void bookRace_shouldThrowExceptions_IfEntryTimeInvalid(){
        //Arrange Act and Assert
        Assertions.assertThrows(InvalidEntryTimeException.class, () -> bookingService.bookRace(VehicleType.CAR, "HA4", LocalTime.parse("18:00")));

    }

}
