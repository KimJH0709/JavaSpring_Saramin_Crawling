package com.kjh.wsd.saramIn_crawling.job.repository;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
}
