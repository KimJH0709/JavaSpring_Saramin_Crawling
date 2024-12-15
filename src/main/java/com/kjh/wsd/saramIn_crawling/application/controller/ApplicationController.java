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

/**
 * 지원 관리 컨트롤러
 * 사용자가 지원 기능을 사용할 수 있도록 API를 제공합니다.
 */
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 사용자가 특정 공고에 지원합니다.
     *
     * @param jobId 지원하려는 공고의 ID
     * @param token 사용자 인증을 위한 JWT 토큰 (쿠키에서 전달됨)
     * @return 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @PostMapping
    @Operation(summary = "지원하기", description = "사용자가 특정 공고에 지원합니다.")
    public ResponseEntity<?> createApplication(
            @RequestParam Long jobId,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 먼저 해주세요.");
        }

        try {
            applicationService.createApplication(token, jobId);
            return ResponseEntity.status(HttpStatus.CREATED).body("지원에 성공하였습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * 사용자의 지원 내역을 조회합니다.
     *
     * @param page  조회할 페이지 번호 (기본값: 0)
     * @param size  페이지당 항목 수 (기본값: 10)
     * @param token 사용자 인증을 위한 JWT 토큰 (쿠키에서 전달됨)
     * @return 사용자의 지원 내역 또는 에러 메시지를 포함한 ResponseEntity
     */
    @GetMapping
    @Operation(summary = "지원 내역 조회", description = "사용자의 지원 내역을 조회합니다.")
    public ResponseEntity<?> getApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 먼저 해주세요.");
        }

        try {
            System.out.println("Access token: " + token);
            PageRequest pageable = PageRequest.of(page, size);
            Page<Application> applications = applicationService.getApplications(token, pageable);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching applications.");
        }
    }

    /**
     * 사용자가 특정 공고에 대한 지원 내역을 취소합니다.
     *
     * @param jobId 취소하려는 공고 ID
     * @param token 사용자 인증을 위한 JWT 토큰 (쿠키에서 전달됨)
     * @return 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @DeleteMapping("/{jobId}")
    @Operation(summary = "지원 취소", description = "특정 공고에 대한 지원 내역을 취소합니다.")
    public ResponseEntity<?> cancelApplication(
            @PathVariable Long jobId,
            @CookieValue(name = "ACCESS_TOKEN", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 먼저 해주세요.");
        }

        try {
            applicationService.cancelApplication(token, jobId);
            return ResponseEntity.ok("지원 내역이 성공적으로 취소되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
