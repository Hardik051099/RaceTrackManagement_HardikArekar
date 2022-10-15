package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.geektrust.backend.appConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.enums.CommandName;
import com.geektrust.backend.exceptions.CommandNotFoundException;

public class App {
	// To run the application  ./gradlew run --args="sample_input/input4.txt"
	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        run(commandLineArgs);
	}
	public static void run(List<String> commandLineArgs) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvokerWithAllCommands();
        BufferedReader reader;
        String inputFile = commandLineArgs.get(0);
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(CommandName.valueOf(tokens.get(0)),tokens);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException | CommandNotFoundException e) {
            e.printStackTrace();
        }
   }

}
