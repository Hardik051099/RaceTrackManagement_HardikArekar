package com.geektrust.backend.commands;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.geektrust.backend.dtos.TotalRevenueDto;
import com.geektrust.backend.services.RevenueService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Calculate Revenue test")
@ExtendWith(MockitoExtension.class)
public class CalculateRevenueCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    RevenueService revenueServiceMock;

    @InjectMocks
    CalculateRevenueCommand calculateRevenueCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of CalculateReveune Should Print total 'Regular Vip' revenue")
    public void execute_ShouldPrintRegularVipRevenue() {
        //Arrange
        Integer regularRevenue = 1650;
        Integer vipRevenue = 1750;
        String expectedResponse = "1650 1750";

        when(revenueServiceMock.calculateTotalRevenue()).thenReturn(new TotalRevenueDto(regularRevenue, vipRevenue));

        //Act
        calculateRevenueCommand.execute(List.of("REVENUE"));

        //Assert
        Assertions.assertEquals(expectedResponse, outputStreamCaptor.toString().trim());
        verify(revenueServiceMock,times(1)).calculateTotalRevenue();
    }



















    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
