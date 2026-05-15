package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.response.ChartDataResponse;
import com.hruthvik.jobtracker.dto.response.DashboardSummaryResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.repository.JobApplicationRepository;
import com.hruthvik.jobtracker.service.AuthService;
import com.hruthvik.jobtracker.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final JobApplicationRepository repository;
    private final AuthService authService;

    @Override
    public DashboardSummaryResponse getSummary() {
        User user = authService.getCurrentUser();
        List<JobApplication> apps = user.getJobApplications();

        long total = apps.size();
        long interviews = apps.stream().filter(a -> a.getStatus() == JobStatus.INTERVIEW || a.getStatus() == JobStatus.HR_ROUND).count();
        long offers = apps.stream().filter(a -> a.getStatus() == JobStatus.OFFER || a.getStatus() == JobStatus.ACCEPTED).count();
        long rejections = apps.stream().filter(a -> a.getStatus() == JobStatus.REJECTED).count();

        Map<String, Long> byStatus = apps.stream()
                .collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()));

        Map<String, Long> bySource = apps.stream()
                .collect(Collectors.groupingBy(a -> a.getSource().name(), Collectors.counting()));

        return DashboardSummaryResponse.builder()
                .totalApplications(total)
                .interviewsCount(interviews)
                .offersCount(offers)
                .rejectionCount(rejections)
                .applicationsByStatus(byStatus)
                .applicationsBySource(bySource)
                .build();
    }

    @Override
    public List<ChartDataResponse> getApplicationsByStatus() {
        User user = authService.getCurrentUser();
        return user.getJobApplications().stream()
                .collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ChartDataResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChartDataResponse> getApplicationsBySource() {
        User user = authService.getCurrentUser();
        return user.getJobApplications().stream()
                .collect(Collectors.groupingBy(a -> a.getSource().name(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ChartDataResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChartDataResponse> getMonthlyTrend() {
        User user = authService.getCurrentUser();
        return user.getJobApplications().stream()
                .collect(Collectors.groupingBy(a -> a.getDateApplied().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ChartDataResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
