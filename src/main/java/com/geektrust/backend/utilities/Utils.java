package com.geektrust.backend.utilities;

import java.time.LocalTime;

public class Utils<T> {

    public static<T> void println (T element){
        System.out.println(element);
    }
    public static boolean isOverlapping(LocalTime entryTime1, LocalTime exitTime1, LocalTime entryTime2, LocalTime exitTime2) {
        return entryTime1.isBefore(exitTime2) && entryTime2.isBefore(exitTime1);
    }
    public static<T> String generateID(T s1,T s2){
        return s1.toString()+Constants.DELIMITER+s2.toString();
    }
}
