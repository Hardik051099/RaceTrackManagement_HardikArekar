package com.geektrust.backend.entities;

import com.geektrust.backend.enums.VehicleType;

public class Bike extends Vehicle {
    
    public Bike (String vehicleNumber){
        super(vehicleNumber, VehicleType.BIKE);
    }

}
