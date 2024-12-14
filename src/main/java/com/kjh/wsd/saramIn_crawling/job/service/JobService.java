package com.kjh.wsd.saramIn_crawling.job.service;

import com.kjh.wsd.saramIn_crawling.auth.security.JwtUtil;
import com.kjh.wsd.saramIn_crawling.job.model.Job;
import com.kjh.wsd.saramIn_crawling.job.repository.JobRepository;
import com.kjh.wsd.saramIn_crawling.job.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository repository;
    private final JwtUtil jwtUtil;

    public Page<Job> getJobs(String keyword, String location, String experience, String salary, Pageable pageable) {
        Specification<Job> spec = Specification.where(null);

        if (keyword != null && !keyword.trim().isEmpty()) {
            String trimmedKeyword = keyword.trim();
            spec = spec.and(
                    Specification.where(JobSpecification.containsTitle(trimmedKeyword))
                            .or(JobSpecification.containsCompany(trimmedKeyword))
                            .or(JobSpecification.containsLocation(trimmedKeyword))
                            .or(JobSpecification.containsExperience(trimmedKeyword))
                            .or(JobSpecification.containsSalary(trimmedKeyword))
                            .or(JobSpecification.containsEmploymentType(trimmedKeyword))
                            .or(JobSpecification.containsRequirements(trimmedKeyword))
                            .or(JobSpecification.containsSector(trimmedKeyword))
            );
        }

        if (location != null && !location.trim().isEmpty()) {
            spec = spec.and(JobSpecification.containsLocation(location.trim()));
        }

        if (experience != null && !experience.trim().isEmpty()) {
            spec = spec.and(JobSpecification.containsExperience(experience.trim()));
        }

        if (salary != null && !salary.trim().isEmpty()) {
            spec = spec.and(JobSpecification.containsSalary(salary.trim()));
        }

        return repository.findAll(spec, pageable);
    }

    public Job getJobById(Long id) {
        Job job = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Job not found"));
        job.setViews(job.getViews() + 1);
        repository.save(job);
        return job;
    }

    public boolean isTokenValid(String token) {
        return jwtUtil.validateToken(token);
    }
}
