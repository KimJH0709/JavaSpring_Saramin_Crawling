package com.kjh.wsd.saramIn_crawling.job.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String company; // 회사 이름

    @Column
    private String location; // 지역

    @Column
    private String link; // 링크

    @Column
    private String experience; // 경력

    @Column
    private String salary; // 연봉

    @Column
    private String position; // 포지션

    @Column(unique = true)
    private String uniqueIdentifier; // 중복 방지

    @Column(nullable = false)
    private int views = 0; // 기본값 0으로 설정
}
