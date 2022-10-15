package com.geektrust.backend.services;

import com.geektrust.backend.dtos.TotalRevenueDto;

public interface IRevenueService {
    public TotalRevenueDto calculateTotalRevenue();
}
