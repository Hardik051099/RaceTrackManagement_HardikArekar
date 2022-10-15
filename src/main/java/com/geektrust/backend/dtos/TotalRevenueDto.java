package com.geektrust.backend.dtos;

public class TotalRevenueDto {
    private final Integer regularRevenue;
    private final Integer vipRevenue;

    public TotalRevenueDto(Integer regularRevenue, Integer vipRevenue) {
        this.regularRevenue = regularRevenue;
        this.vipRevenue = vipRevenue;
    }

    public Integer getRegularRevenue() {
        return regularRevenue;
    }

    public Integer getVipRevenue() {
        return vipRevenue;
    }

    @Override
    public String toString() {
        return  regularRevenue + " " + vipRevenue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((regularRevenue == null) ? 0 : regularRevenue.hashCode());
        result = prime * result + ((vipRevenue == null) ? 0 : vipRevenue.hashCode());
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
        TotalRevenueDto other = (TotalRevenueDto) obj;
        if (regularRevenue == null) {
            if (other.regularRevenue != null)
                return false;
        } else if (!regularRevenue.equals(other.regularRevenue))
            return false;
        if (vipRevenue == null) {
            if (other.vipRevenue != null)
                return false;
        } else if (!vipRevenue.equals(other.vipRevenue))
            return false;
        return true;
    }
    
}
