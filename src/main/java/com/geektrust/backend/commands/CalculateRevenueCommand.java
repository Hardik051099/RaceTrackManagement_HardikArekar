package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.dtos.TotalRevenueDto;
import com.geektrust.backend.services.IRevenueService;
import static com.geektrust.backend.utilities.Utils.println;


public class CalculateRevenueCommand implements ICommand {
    private final IRevenueService revenueService;
    private final String SINGLE_SPACE = " ";
    public CalculateRevenueCommand(IRevenueService revenueService) {
        this.revenueService = revenueService;
    }

    //REVENUE
    @Override
    public void execute(List<String> tokens) {
        TotalRevenueDto totalRevenue= revenueService.calculateTotalRevenue();
        Integer regularRevenue = totalRevenue.getRegularRevenue();
        Integer vipRevenue = totalRevenue.getVipRevenue();
        println(formatRevenueOutput(regularRevenue, vipRevenue));
    }

    private String formatRevenueOutput(Integer regularRevenue , Integer vipRevenue){
        return regularRevenue+SINGLE_SPACE+vipRevenue;
    }
    
}
