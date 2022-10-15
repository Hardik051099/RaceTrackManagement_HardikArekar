package com.geektrust.backend.services;

import java.time.LocalTime;
import com.geektrust.backend.dtos.RaceBookingDto;
import com.geektrust.backend.exceptions.InvalidExitTimeException;
import com.geektrust.backend.exceptions.RaceNotFoundException;
import com.geektrust.backend.exceptions.RaceTrackFullException;
import com.geektrust.backend.exceptions.RaceTrackNotFoundException;

public interface IExtensionService {
    public RaceBookingDto extendRace(String vehicleNumber, LocalTime exitTime) throws RaceTrackFullException,InvalidExitTimeException,RaceNotFoundException,RaceTrackNotFoundException;
}
