package com.geektrust.backend.entities;

import com.geektrust.backend.enums.VehicleType;

public class Car extends Vehicle {
    
    public Car (String vehicleNumber){
        super(vehicleNumber, VehicleType.CAR);
    }

}
