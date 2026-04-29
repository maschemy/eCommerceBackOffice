package com.ecommercebackoffice.dashboard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DashboardResponseDto {
    private final DashboardSummaryDto summaryDto;
    private final DashboradWidgetsDto widgetsDto;
    private final DashboardChartsDto chartsDto;
    private final List<DashboardRecentOrderDto> recentDto;

    public DashboardResponseDto(DashboardSummaryDto summaryDto,
                                DashboradWidgetsDto widgetsDto,
                                DashboardChartsDto chartsDto,
                                List<DashboardRecentOrderDto> recentDto) {
        this.summaryDto = summaryDto;
        this.widgetsDto = widgetsDto;
        this.chartsDto = chartsDto;
        this.recentDto = recentDto;
    }
}
