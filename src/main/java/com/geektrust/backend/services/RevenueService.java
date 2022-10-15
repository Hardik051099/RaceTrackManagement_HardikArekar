package com.geektrust.backend.services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.geektrust.backend.dtos.TotalRevenueDto;
import com.geektrust.backend.entities.Race;
import com.geektrust.backend.entities.RaceTrack;
import com.geektrust.backend.enums.TrackType;
import com.geektrust.backend.repositories.IRaceTrackRepository;
import com.geektrust.backend.utilities.Constants;

public class RevenueService implements IRevenueService {
    private final IRaceTrackRepository raceTrackRepository;
    private final Integer NO_OF_REVENUES = 2;
    private final Integer SINGLE_VARIABLE = 1;
    private final Integer NO_REMAINDER = 0;
    public RevenueService(IRaceTrackRepository raceTrackRepository) {
        this.raceTrackRepository = raceTrackRepository;
    }

    @Override
    public TotalRevenueDto calculateTotalRevenue() {
        final List<RaceTrack> raceTrackList = raceTrackRepository.fetchAll();
        final int[] totalRevenue = new int[NO_OF_REVENUES]; //totalRevenue[0] ==> Regular , totalRevenue[1] ==> VIP
        raceTrackList.stream()
        .forEach(raceTrack -> {
            if(raceTrack.getTrackType().equals(TrackType.REGULAR)){
                totalRevenue[0] += getTotalFromRaceList(raceTrack.getRaceList(), raceTrack);
            }
            else{
                totalRevenue[1] += getTotalFromRaceList(raceTrack.getRaceList(), raceTrack);
            }
        });
        return new TotalRevenueDto(totalRevenue[0], totalRevenue[1]);
    }

    private Integer getTotalFromRaceList(List<Race> raceList, RaceTrack raceTrack) {
        int[] totalSum = new int[SINGLE_VARIABLE];
        raceList.stream()
        .forEach(race -> {
            totalSum[0] += calculateChargeForDuration(raceTrack.getCostPerHour(), race);
        });
        return totalSum[0];
    }
    
    private Integer getTimeDifference(LocalTime time1, LocalTime time2){
        return (int) ChronoUnit.MINUTES.between(time1, time2);
    }

    private Integer extraMinsToChargebleHours (Integer extraMins){
        Integer minsToChargebleHours = extraMins/Constants.ONE_HOUR;
        if(extraMins%Constants.ONE_HOUR == NO_REMAINDER){
            return minsToChargebleHours;
        }
        return ++minsToChargebleHours;
    }

    private Integer calculateChargeForDuration(Integer costPerHour , Race race){
        Integer durationOfRaceInMin = getTimeDifference(race.getEntryTime(), race.getExitTime());
        Integer extraDuration = durationOfRaceInMin - Constants.DEFAULT_DURATION_MINUTES;
        Integer defaultDurationCharge = costPerHour*Constants.DEFAULT_DURATION_HOURS;

        if(durationOfRaceInMin <= Constants.DEFAULT_DURATION_MINUTES || extraDuration <= Constants.FREE_EXTENSION_MIN){
            return defaultDurationCharge;
        }
        else {
            Integer extraCharge = extraMinsToChargebleHours(extraDuration)*Constants.EXTENSION_CHARGE_PER_HOUR;
            return defaultDurationCharge + extraCharge;
        }
    }
    
}
