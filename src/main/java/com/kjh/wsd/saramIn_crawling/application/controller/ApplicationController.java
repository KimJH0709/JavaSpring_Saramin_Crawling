package com.kjh.wsd.saramIn_crawling.application.controller;

import com.kjh.wsd.saramIn_crawling.application.model.Application;
import com.kjh.wsd.saramIn_crawling.application.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "지원하기", description = "사용자가 특정 공고에 지원합니다.")
    public ResponseEntity<?> createApplication(
            @RequestParam Long jobId,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing token");
        }

        try {
            Application application = applicationService.createApplication(token, jobId);
            return ResponseEntity.status(HttpStatus.CREATED).body(application);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "지원 내역 조회", description = "사용자의 지원 내역을 조회합니다.")
    public ResponseEntity<?> getApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing token");
        }

        try {
            PageRequest pageable = PageRequest.of(page, size);
            Page<Application> applications = applicationService.getApplications(token, pageable);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching applications.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지원 취소", description = "특정 지원을 취소합니다.")
    public ResponseEntity<?> cancelApplication(
            @PathVariable Long id,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing token");
        }

        try {
            applicationService.cancelApplication(token, id);
            return ResponseEntity.ok("Application cancelled successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
