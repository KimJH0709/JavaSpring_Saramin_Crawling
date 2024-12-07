package com.kjh.wsd.saramIn_crawling.job.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponse {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String experience;
    private String salary;
    private String position;
    private String link;
    private int views;
}
