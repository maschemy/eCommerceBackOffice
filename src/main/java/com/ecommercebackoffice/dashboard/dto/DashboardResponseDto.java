package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class DashboardResponseDto {
    private final DashboardSummaryDto summaryDto;
    private final DashboradWidgetsDto widgetsDto;
//    private final DashboardChartsDto chartsDto;
    private final DashboardRecentDto recentDto;

    public DashboardResponseDto(DashboardSummaryDto summaryDto, DashboradWidgetsDto widgetsDto, DashboardRecentDto recentDto) {
        this.summaryDto = summaryDto;
        this.widgetsDto = widgetsDto;
        this.recentDto = recentDto;
    }
}
