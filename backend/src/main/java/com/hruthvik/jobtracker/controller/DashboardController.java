package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.response.ChartDataResponse;
import com.hruthvik.jobtracker.dto.response.DashboardSummaryResponse;
import com.hruthvik.jobtracker.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/charts/status")
    public ResponseEntity<List<ChartDataResponse>> getByStatus() {
        return ResponseEntity.ok(dashboardService.getApplicationsByStatus());
    }

    @GetMapping("/charts/source")
    public ResponseEntity<List<ChartDataResponse>> getBySource() {
        return ResponseEntity.ok(dashboardService.getApplicationsBySource());
    }

    @GetMapping("/charts/trend")
    public ResponseEntity<List<ChartDataResponse>> getTrend() {
        return ResponseEntity.ok(dashboardService.getMonthlyTrend());
    }
}
