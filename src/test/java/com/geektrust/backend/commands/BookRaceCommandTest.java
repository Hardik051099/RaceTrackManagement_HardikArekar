package com.geektrust.backend.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.List;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.entities.Bike;
import com.geektrust.backend.entities.BikeTrack;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.services.BookingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("BookRaceCommand test")
@ExtendWith(MockitoExtension.class)
public class BookRaceCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    BookingService bookingServiceMock;

    @InjectMocks
    BookRaceCommand bookRaceCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of BookRaceCommand Should Print SUCCESS if race is booked successfully")
    public void execute_ShouldPrintSUCESS_BookingRaceSuccessful() {
        //Arrange
        String vehicleNumber = "HA20";
        String vehicleType = "BIKE";
        String entryTime = "13:00";
        String expectedResponse = Response.SUCCESS.toString();

        RaceTrack bikeTrack = new BikeTrack();
        Vehicle bike = new Bike("HA20");
        Race bikeRace1 = new Race(bike, LocalTime.parse("13:00"));
        bikeTrack.addNewRaceToTrack(bikeRace1);

        when(bookingServiceMock.bookRace(any(), any(),any())).thenReturn(new RaceBookingDto(bikeRace1, bikeTrack));

        //Act
        bookRaceCommand.execute(List.of("BOOK",vehicleType,vehicleNumber,entryTime));

        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(bookingServiceMock,times(1)).bookRace(any(),any(),any());
    }

    @Test
    @DisplayName("execute method of BookRaceCommand Should Print RACETRACK_FULL if raceTrack is not available")
    public void execute_ShouldPrintRACETRACKFULL_RaceTrackFull() {
        //Arrange
        String expectedResponse = Response.RACETRACK_FULL.toString();
        String vehicleNumber = "HA20";
        String vehicleType = "BIKE";
        String entryTime = "13:00";

        doThrow(new RaceTrackFullException()).when(bookingServiceMock).bookRace(any(),any(),any());
        //Act
        bookRaceCommand.execute(List.of("BOOK",vehicleType,vehicleNumber,entryTime));
        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(bookingServiceMock,times(1)).bookRace(any(),any(),any());
    }

    @Test
    @DisplayName("execute method of BookRaceCommand Should Print INVALID_ENTRY_TIME if entry time of race is invalid")
    public void execute_ShouldPrintInvalidEntry_EntryTimeisNotValid() {
        //Arrange
        String expectedResponse = Response.INVALID_ENTRY_TIME.toString();
        String vehicleNumber = "HA20";
        String vehicleType = "BIKE";
        String entryTime = "13:00";

        doThrow(new InvalidEntryTimeException()).when(bookingServiceMock).bookRace(any(),any(),any());
        //Act
        bookRaceCommand.execute(List.of("BOOK",vehicleType,vehicleNumber,entryTime));
        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(bookingServiceMock,times(1)).bookRace(any(),any(),any());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
