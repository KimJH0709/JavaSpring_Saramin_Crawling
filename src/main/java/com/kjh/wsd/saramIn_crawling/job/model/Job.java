package com.kjh.wsd.saramIn_crawling.job.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String url;
    private String deadline;
    private String location;
    private String experience;
    private String requirements;
    private String employmentType;
    private String salary;
    private String sector;

    private int views;
}
