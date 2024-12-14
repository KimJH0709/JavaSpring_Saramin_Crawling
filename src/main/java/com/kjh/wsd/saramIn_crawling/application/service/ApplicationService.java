package com.kjh.wsd.saramIn_crawling.application.service;

import com.kjh.wsd.saramIn_crawling.application.model.Application;
import com.kjh.wsd.saramIn_crawling.application.repository.ApplicationRepository;
import com.kjh.wsd.saramIn_crawling.auth.security.JwtUtil;
import com.kjh.wsd.saramIn_crawling.job.model.Job;
import com.kjh.wsd.saramIn_crawling.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JwtUtil jwtUtil;

    // 지원 저장 (중복 방지 포함)
    public Application createApplication(String token, Long jobId) {
        String username = jwtUtil.extractUsername(token);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        String uniqueKey = generateUniqueKey(username, jobId);

        if (applicationRepository.existsByUniqueKey(uniqueKey)) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        Application application = Application.builder()
                .username(username)
                .job(job)
                .uniqueKey(uniqueKey)
                .appliedAt(LocalDateTime.now())
                .build();

        return applicationRepository.save(application);
    }

    // 지원 내역 조회
    public Page<Application> getApplications(String token, Pageable pageable) {
        String username = jwtUtil.extractUsername(token);
        return applicationRepository.findByUsername(username, pageable);
    }

    // 지원 취소
    public void cancelApplication(String token, Long applicationId) {
        String username = jwtUtil.extractUsername(token);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (!application.getUsername().equals(username)) {
            throw new IllegalStateException("Unauthorized to cancel this application");
        }

        applicationRepository.delete(application);
    }

    // 유니크 키 생성 로직
    private String generateUniqueKey(String username, Long jobId) {
        return username + "_" + jobId;
    }
}
