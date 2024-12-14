package com.kjh.wsd.saramIn_crawling.application.model;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false, unique = true)
    private String uniqueKey;

    @Column(nullable = false)
    private LocalDateTime appliedAt;
}
