package com.geektrust.backend.commands;

import java.time.LocalTime;
import java.util.List;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.exceptions.RaceNotFoundException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;
import com.geektrust.backend.services.IExtensionService;
import static com.geektrust.backend.utilities.Utils.println;


public class ExtendRaceCommand implements ICommand {
    private final IExtensionService extensionService;
    
    public ExtendRaceCommand(IExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    //ADDITIONAL M40 17:40
    @Override
    public void execute(List<String> tokens) {
        String vehicleNumber = tokens.get(1);
        String exitTime = tokens.get(2);
        
        try {
            extensionService.extendRace(vehicleNumber, LocalTime.parse(exitTime));
            println(Response.SUCCESS);
        }
        catch (RaceTrackFullException e){
            println(Response.RACETRACK_FULL);
        }
        catch (InvalidExitTimeException e){
            println(Response.INVALID_EXIT_TIME);
        }
        catch (RaceNotFoundException | RaceTrackNotFoundException e){
            println(e.getMessage());
        }
    }
    
}
