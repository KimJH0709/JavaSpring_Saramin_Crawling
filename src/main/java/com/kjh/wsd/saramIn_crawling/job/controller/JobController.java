package com.kjh.wsd.saramIn_crawling.job.controller;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import com.kjh.wsd.saramIn_crawling.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService service;

    @GetMapping
    public ResponseEntity<Page<Job>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "views") String sort,
            @RequestParam(required = false, defaultValue = "") String location,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String experience,
            @RequestParam(required = false, defaultValue = "") String salary
    ) {
        System.out.println("Request Parameters - location: [" + location + "], keyword: [" + keyword +
                "], experience: [" + experience + "], salary: [" + salary + "]");
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Job> jobs = service.getJobs(keyword, location, experience, salary, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return new ResponseEntity<>(jobs, headers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        Job job = service.getJobById(id);
        if (job == null) {
            System.out.println("No job found with ID: " + id);
        }
        return job;
    }

}
