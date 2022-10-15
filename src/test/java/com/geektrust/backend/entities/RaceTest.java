package com.geektrust.backend.entities;
import java.time.LocalTime;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Race Test")
public class RaceTest {
    @Test
    @DisplayName("setExitTime method Should set new Exit Time for race")
    public void setExitTime_ShouldSetNewExitTime(){
        // Arrange
        Vehicle car = new Car("HA22");
        Race carRace = new Race(car, LocalTime.parse("14:00"));
        
        LocalTime expectedExitTime = LocalTime.parse("18:00");
        // Act
        LocalTime actualExitTime = carRace.setExitTime(LocalTime.parse("18:00"));
        // Assert
        Assertions.assertEquals(expectedExitTime, actualExitTime);
    }

    @Test
    @DisplayName("setExitTime method Should throw InvalidExitTimeException for invalid exit time")
    public void setExitTime_ShouldThrowException_InvalidExitTime(){
        // Arrange
        Vehicle car = new Car("HA22");
        Race carRace = new Race(car, LocalTime.parse("14:00"));
        // Act Assert
        Assertions.assertThrows(InvalidExitTimeException.class, () -> carRace.setExitTime(LocalTime.parse("21:00")));
    }
}
