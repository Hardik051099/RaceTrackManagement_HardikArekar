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
import com.geektrust.backend.entities.Car;
import com.geektrust.backend.entities.CarTrack;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.entities.Vehicle;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.services.ExtensionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ExtendRaceCommand test")
@ExtendWith(MockitoExtension.class)
public class ExtendRaceCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    ExtensionService extensionServiceMock;

    @InjectMocks
    ExtendRaceCommand extendRaceCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of ExtendRaceCommand Should Print SUCCESS if race is extended successfully")
    public void execute_ShouldPrintSUCESS_ExtendRaceSuccessful() {
        //Arrange
        String vehicleNumber = "HA30";
        String exitTime = "17:00";
        String expectedResponse = Response.SUCCESS.toString();

        RaceTrack carTrack = new CarTrack();
        Vehicle car = new Car("HA20");
        Race carRace1 = new Race(car, LocalTime.parse("13:00"));
        carRace1.setExitTime(LocalTime.parse(exitTime));

        when(extensionServiceMock.extendRace(any(), any())).thenReturn(new RaceBookingDto(carRace1, carTrack));

        //Act
        extendRaceCommand.execute(List.of("ADDITIONAL",vehicleNumber,exitTime));

        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(extensionServiceMock,times(1)).extendRace(any(),any());
    }

    @Test
    @DisplayName("execute method of ExtendRaceTrack Should Print RACETRACK_FULL if racetrack not available for extension")
    public void execute_ShouldPrintRACETRACKFULL_RaceTrackFullForExtension() {
        //Arrange
        String expectedResponse = Response.RACETRACK_FULL.toString();
        String vehicleNumber = "HA20";
        String exitTime = "18:00";

        doThrow(new RaceTrackFullException()).when(extensionServiceMock).extendRace(any(),any());
        //Act
        extendRaceCommand.execute(List.of("ADDITIONAL",vehicleNumber,exitTime));
        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(extensionServiceMock,times(1)).extendRace(any(),any());
    }

    @Test
    @DisplayName("execute method of ExtendRaceCommand Should Print INVALID_EXIT_TIME if exit time of race is invalid")
    public void execute_ShouldPrintInvalidExit_ExitTimeisNotValid() {
        //Arrange
        String expectedResponse = Response.INVALID_EXIT_TIME.toString();
        String vehicleNumber = "HA20";
        String exitTime = "18:00";

        doThrow(new InvalidExitTimeException()).when(extensionServiceMock).extendRace(any(),any());
        //Act
        extendRaceCommand.execute(List.of("ADDITIONAL",vehicleNumber,exitTime));
        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(extensionServiceMock,times(1)).extendRace(any(),any());
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
