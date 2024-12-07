package com.kjh.wsd.saramIn_crawling.job.repository;

import com.kjh.wsd.saramIn_crawling.job.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    @Query("SELECT jp FROM JobPosting jp " +
            "WHERE (:location IS NULL OR jp.location LIKE %:location%) " +
            "AND (:experience IS NULL OR jp.experience LIKE %:experience%) " +
            "AND (:salary IS NULL OR jp.salary LIKE %:salary%) " +
            "AND (:position IS NULL OR jp.position LIKE %:position%)")
    Page<JobPosting> findFilteredJobs(
            @Param("location") String location,
            @Param("experience") String experience,
            @Param("salary") String salary,
            @Param("position") String position,
            Pageable pageable
    );
    boolean existsByUniqueIdentifier(String uniqueIdentifier);
}
