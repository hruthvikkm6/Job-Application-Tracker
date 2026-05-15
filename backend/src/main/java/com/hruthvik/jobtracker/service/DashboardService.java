package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.response.ChartDataResponse;
import com.hruthvik.jobtracker.dto.response.DashboardSummaryResponse;

import java.util.List;

public interface DashboardService {
    DashboardSummaryResponse getSummary();
    List<ChartDataResponse> getApplicationsByStatus();
    List<ChartDataResponse> getApplicationsBySource();
    List<ChartDataResponse> getMonthlyTrend();
}
