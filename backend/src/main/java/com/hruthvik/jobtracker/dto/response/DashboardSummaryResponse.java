package com.hruthvik.jobtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryResponse {
    private long totalApplications;
    private long interviewsCount;
    private long offersCount;
    private long rejectionCount;
    private Map<String, Long> applicationsByStatus;
    private Map<String, Long> applicationsBySource;
}
