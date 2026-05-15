package com.hruthvik.jobtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.Source;
import com.hruthvik.jobtracker.service.JobApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security for controller tests or use @WithMockUser
class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService jobApplicationService;

    @MockBean
    private com.hruthvik.jobtracker.security.JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private JobApplicationRequest request;
    private JobApplicationResponse response;

    @BeforeEach
    void setUp() {
        request = JobApplicationRequest.builder()
                .companyName("Google")
                .jobTitle("Software Engineer")
                .status(JobStatus.APPLIED)
                .source(Source.LINKEDIN)
                .location("Remote")
                .jobType(com.hruthvik.jobtracker.entity.JobType.ONSITE)
                .dateApplied(LocalDate.now())
                .build();

        response = JobApplicationResponse.builder()
                .id(1L)
                .companyName("Google")
                .jobTitle("Software Engineer")
                .status(JobStatus.APPLIED)
                .source(Source.LINKEDIN)
                .build();
    }

    @Test
    @WithMockUser
    void create_ShouldReturnCreated() throws Exception {
        when(jobApplicationService.create(any(JobApplicationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/jobs")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("Google"))
                .andExpect(jsonPath("$.jobTitle").value("Software Engineer"));
    }
}
