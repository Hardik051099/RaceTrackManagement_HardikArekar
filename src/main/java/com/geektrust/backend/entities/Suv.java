package com.geektrust.backend.entities;

import com.geektrust.backend.enums.VehicleType;

public class Suv extends Vehicle {
    
    public Suv (String vehicleNumber){
        super(vehicleNumber, VehicleType.SUV);
    }

}
