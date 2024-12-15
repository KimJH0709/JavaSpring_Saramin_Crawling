package com.kjh.wsd.saramIn_crawling.application.repository;

import com.kjh.wsd.saramIn_crawling.application.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByUniqueKey(String uniqueKey);
    Page<Application> findByUsername(String username, Pageable pageable);
    Optional<Application> findByUsernameAndJobId(String username, Long jobId);
}
