package com.geektrust.backend.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.enums.CommandName;
import com.geektrust.backend.exceptions.CommandNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Command invoker Test")
@ExtendWith(MockitoExtension.class)

public class CommandInvokerTest {
    @Mock
    private ICommand commandMock;

    @Mock
    private Map <String,ICommand> commandMap;

    @InjectMocks
    private CommandInvoker commandInvoker;


    @Test
    @DisplayName("executeCommand Should execute registered command")
    public void executeCommand_ShouldExecuteCommand() throws Exception{
        // Arrange
        CommandName commandName = CommandName.BOOK;
        String vehicleNumber = "BK12";
        String entryTime = "14:00";
        commandInvoker.registerCommand(commandName, commandMock);
        // Act
        commandInvoker.executeCommand(commandName, List.of(commandName.toString(),vehicleNumber,entryTime));
        // Assert
        verify(commandMock,times(1)).execute(anyList());

    }

    @Test
    @DisplayName("executeCommand Should throw Command not found exception for unregistered command")
    public void executeCommand_ShouldThrowException_NoCommandFound() throws Exception{
        // Arrange
        CommandName commandName = CommandName.ADDITIONAL;
        String vehicleNumber = "CR12";
        String entryTime = "13:00";
        // Act
        // Assert
        assertThrows(CommandNotFoundException.class, () -> commandInvoker.executeCommand(commandName, List.of(commandName.toString(),vehicleNumber,entryTime)));
    }

}
