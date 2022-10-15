package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.BookRaceCommand;
import com.geektrust.backend.commands.CalculateRevenueCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ExtendRaceCommand;
import com.geektrust.backend.enums.CommandName;
import com.geektrust.backend.repositories.IRaceRepository;
import com.geektrust.backend.repositories.IRaceTrackRepository;
import com.geektrust.backend.repositories.RaceRepository;
import com.geektrust.backend.repositories.RaceTrackRepository;
import com.geektrust.backend.services.BookingService;
import com.geektrust.backend.services.ExtensionService;
import com.geektrust.backend.services.IBookingService;
import com.geektrust.backend.services.IExtensionService;
import com.geektrust.backend.services.IRevenueService;
import com.geektrust.backend.services.RevenueService;

public class ApplicationConfig {

    private final IRaceTrackRepository raceTrackRepository = new RaceTrackRepository();
    private final IRaceRepository raceRepository = new RaceRepository();

    private final IBookingService bookingService = new BookingService(raceTrackRepository, raceRepository);
    private final IExtensionService extensionService = new ExtensionService(raceTrackRepository, raceRepository);
    private final IRevenueService revenueService = new RevenueService(raceTrackRepository);

    private final BookRaceCommand bookRaceCommand = new BookRaceCommand(bookingService);
    private final ExtendRaceCommand extendRaceCommand = new ExtendRaceCommand(extensionService);
    private final CalculateRevenueCommand calculateRevenueCommand = new CalculateRevenueCommand(revenueService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvokerWithAllCommands(){
        commandInvoker.registerCommand(CommandName.BOOK, bookRaceCommand);
        commandInvoker.registerCommand(CommandName.ADDITIONAL, extendRaceCommand);
        commandInvoker.registerCommand(CommandName.REVENUE,calculateRevenueCommand);

        return commandInvoker;
    }
}
