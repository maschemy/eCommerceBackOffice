package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class DashboardResponseDto {
    private final DashboardSummaryDto dashboardSummaryDto;
    private final DashboradWidgetsDto widgetsDto;
//    private final ChartsDto chartsDto;
//    private final RecentDto recentDto;


    public DashboardResponseDto(DashboardSummaryDto dashboardSummaryDto, DashboradWidgetsDto widgetsDto) {
        this.dashboardSummaryDto = dashboardSummaryDto;
        this.widgetsDto = widgetsDto;
    }
}
