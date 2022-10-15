package com.geektrust.backend.entities;

import com.geektrust.backend.enums.VehicleType;

public class Vehicle {
    private final String vehicleNumber;
    private final VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
        result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        if (vehicleNumber == null) {
            if (other.vehicleNumber != null)
                return false;
        } else if (!vehicleNumber.equals(other.vehicleNumber))
            return false;
        if (vehicleType != other.vehicleType)
            return false;
        return true;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
    
}
