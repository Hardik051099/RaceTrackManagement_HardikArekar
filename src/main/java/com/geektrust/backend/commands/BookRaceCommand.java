package com.geektrust.backend.commands;

import java.time.LocalTime;
import java.util.List;
import com.geektrust.backend.enums.Response;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.services.IBookingService;
import static com.geektrust.backend.utilities.Utils.println;

public class BookRaceCommand implements ICommand {

    private final IBookingService bookingService;
    
    public BookRaceCommand(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    //BOOK BIKE M40 14:00
    @Override
    public void execute(List<String> tokens) {
        String vehicleType = tokens.get(1);
        String vehicleNumber = tokens.get(2);
        String entryTime = tokens.get(3);
        try{
            bookingService.bookRace(VehicleType.valueOf(vehicleType), vehicleNumber, LocalTime.parse(entryTime));
            println(Response.SUCCESS);
        }
        catch (RaceTrackFullException e){
            println(Response.RACETRACK_FULL);
        }
        catch (InvalidEntryTimeException e){
            println(Response.INVALID_ENTRY_TIME);
        }
    }
    
}
