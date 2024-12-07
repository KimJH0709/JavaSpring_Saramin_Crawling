package com.kjh.wsd.saramIn_crawling.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    private String location;
    private String experience;
    private String salary;
    private String position;
    private String keyword;
    private int page;
    private int size;
}
