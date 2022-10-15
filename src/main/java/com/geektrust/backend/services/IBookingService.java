package com.geektrust.backend.services;

import java.time.LocalTime;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.enums.VehicleType;
import com.geektrust.backend.exceptions.InvalidEntryTimeException;
import com.geektrust.backend.exceptions.RaceTrackFullException;

public interface IBookingService {
    public RaceBookingDto bookRace(VehicleType vehicleType, String vehicleNumber, LocalTime entryTime) throws RaceTrackFullException,InvalidEntryTimeException;
}
