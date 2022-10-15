package com.geektrust.backend.utilities;

import java.time.LocalTime;

public class Constants {
    public static final Integer DEFAULT_DURATION_HOURS = 3;
    public static final Integer DEFAULT_DURATION_MINUTES = 180;
    public static final Integer FREE_EXTENSION_MIN = 15;
    public static final Integer ONE_HOUR = 60;
    public static final Integer EXTENSION_CHARGE_PER_HOUR = 50;

    private static final String FIVE_PM = "17:00";
    private static final String EIGHT_PM = "20:00";
    private static final String ONE_PM = "13:00";


    public static final LocalTime FINAL_BOOKING_TIME = LocalTime.parse(FIVE_PM);
    public static final LocalTime RACE_CLOSE_TIME = LocalTime.parse(EIGHT_PM);
    public static final LocalTime START_TIME = LocalTime.parse(ONE_PM);
    public static final LocalTime END_TIME = LocalTime.parse(EIGHT_PM);

    public static final String DELIMITER = "_";

}
