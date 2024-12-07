package com.kjh.wsd.saramIn_crawling.job.controller;

import com.kjh.wsd.saramIn_crawling.job.dto.JobRequest;
import com.kjh.wsd.saramIn_crawling.job.dto.JobResponse;
import com.kjh.wsd.saramIn_crawling.job.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobPostingService jobPostingService;

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experience,
            @RequestParam(required = false) String salary,
            @RequestParam(required = false) String position,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) throws IOException {
        JobRequest jobRequest = JobRequest.builder()
                .location(location)
                .experience(experience)
                .salary(salary)
                .position(position)
                .size(size)
                .build();

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<JobResponse> jobs = jobPostingService.getJobs(jobRequest, pageRequest);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse job = jobPostingService.getJobById(id);
        return ResponseEntity.ok(job);
    }
}
