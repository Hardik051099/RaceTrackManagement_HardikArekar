package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.enums.CommandName;
import com.geektrust.backend.exceptions.CommandNotFoundException;

public class CommandInvoker {
    private static final Map<CommandName,ICommand> commandMap = new HashMap<>();

    public void registerCommand(CommandName commandName, ICommand command){
        commandMap.put(commandName,command);
    }

    private ICommand getCommand(CommandName commandName){
        return commandMap.get(commandName);
    }

    public void executeCommand(CommandName commandName, List<String> tokens){
        ICommand command = getCommand(commandName);
        if(command==null){
            throw new CommandNotFoundException();
        }
        command.execute(tokens);
    }
    
}
