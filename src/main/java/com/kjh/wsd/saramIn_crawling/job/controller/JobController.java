package com.kjh.wsd.saramIn_crawling.job.controller;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import com.kjh.wsd.saramIn_crawling.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "채용 공고 목록 조회", description = "페이지네이션, 정렬, 필터를 이용해 채용 공고 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<Job>> getJobs(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지 크기", example = "20")
            @RequestParam(defaultValue = "20") int size,

            @Parameter(description = "정렬 기준 (예: views)", example = "views")
            @RequestParam(defaultValue = "views") String sort,

            @Parameter(description = "근무 위치 필터", example = "Seoul")
            @RequestParam(required = false, defaultValue = "") String location,

            @Parameter(description = "키워드 필터", example = "Java")
            @RequestParam(required = false, defaultValue = "") String keyword,

            @Parameter(description = "요구 경력 필터", example = "2 years")
            @RequestParam(required = false, defaultValue = "") String experience,

            @Parameter(description = "급여 필터", example = "Negotiable")
            @RequestParam(required = false, defaultValue = "") String salary
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Job> jobs = service.getJobs(keyword, location, experience, salary, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return new ResponseEntity<>(jobs, headers, HttpStatus.OK);
    }

    @Operation(summary = "채용 공고 상세 조회", description = "ID를 이용해 특정 채용 공고의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public Job getJobById(
            @Parameter(description = "채용 공고 ID", example = "101") @PathVariable Long id
    ) {
        Job job = service.getJobById(id);
        if (job == null) {
            System.out.println("No job found with ID: " + id);
        }
        return job;
    }
}
