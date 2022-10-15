package com.geektrust.backend.appConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.geektrust.backend.commands.BookRaceCommand;
import com.geektrust.backend.commands.CalculateRevenueCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ExtendRaceCommand;
import com.geektrust.backend.enums.CommandName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Application Config Test")
@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private BookRaceCommand bookRaceCommandMock;

    @Mock
    private ExtendRaceCommand extendRaceCommandMock;

    @Mock
    private CalculateRevenueCommand calculateRevenueCommandMock;

    @Mock
    private CommandInvoker commandInvokerMock;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    @DisplayName("getCommandInvokerWithAllCommands Should return command invoker with registered commands")
    public void getCommandInvokerWithAllCommands_ShouldReturnWithRegisteredCommands() throws Exception{
        // Arrange
        CommandName bookCommand = CommandName.BOOK;
        CommandName extendCommand = CommandName.ADDITIONAL;
        CommandName revenueCommand = CommandName.REVENUE;
        commandInvokerMock.registerCommand(bookCommand, bookRaceCommandMock);
        commandInvokerMock.registerCommand(extendCommand, extendRaceCommandMock);
        commandInvokerMock.registerCommand(revenueCommand, calculateRevenueCommandMock);

        // Act
        applicationConfig.getCommandInvokerWithAllCommands();
        // Assert
        verify(commandInvokerMock,times(3)).registerCommand(any(), any());

    }
    
}
