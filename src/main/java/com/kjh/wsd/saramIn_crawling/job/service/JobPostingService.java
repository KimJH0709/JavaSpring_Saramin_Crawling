package com.kjh.wsd.saramIn_crawling.job.service;

import com.kjh.wsd.saramIn_crawling.job.HtmlParser;
import com.kjh.wsd.saramIn_crawling.job.dto.JobRequest;
import com.kjh.wsd.saramIn_crawling.job.dto.JobResponse;
import com.kjh.wsd.saramIn_crawling.job.model.JobPosting;
import com.kjh.wsd.saramIn_crawling.job.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository repository;

    public Page<JobResponse> getJobs(JobRequest request, PageRequest pageRequest) throws IOException {
        Page<JobPosting> jobs = repository.findFilteredJobs(
                request.getLocation(),
                request.getExperience(),
                request.getSalary(),
                request.getPosition(),
                pageRequest
        );

        if (jobs.isEmpty()) {
            crawlSaramin(request);
            jobs = repository.findFilteredJobs(
                    request.getLocation(),
                    request.getExperience(),
                    request.getSalary(),
                    request.getPosition(),
                    pageRequest
            );
        }

        return jobs.map(this::toJobResponse);
    }

    private void crawlSaramin(JobRequest request) throws IOException {
        String baseUrl = "https://www.saramin.co.kr/zf_user/search/recruit";
        int size = request.getSize();
        int currentPage = 0;

        while (size > 0) {
            String url = String.format("%s?loc_cd=%s&exp_cd=%s&searchword=%s&recruitPage=%d&recruitPageCount=20",
                    baseUrl,
                    request.getLocation(),
                    request.getExperience(),
                    request.getPosition(),
                    currentPage);

            Document document = HtmlParser.connectToUrl(url);
            Elements jobElements = HtmlParser.selectElements(document, ".item_recruit");

            if (jobElements.isEmpty()) {
                break;
            }

            List<JobPosting> newJobs = new ArrayList<>();
            for (Element jobElement : jobElements) {
                String title = jobElement.select(".job_tit a").text();
                String company = jobElement.select(".corp_name a").text();
                String location = jobElement.select(".job_condition span:nth-child(1)").text();
                String experience = jobElement.select(".job_condition span:nth-child(2)").text();
                String salary = jobElement.select(".job_condition span:nth-child(3)").text();
                String position = jobElement.select(".job_sector a").text();
                String link = "https://www.saramin.co.kr" + jobElement.select(".job_tit a").attr("href");

                String uniqueIdentifier = title + company;

                if (!repository.existsByUniqueIdentifier(uniqueIdentifier)) {
                    JobPosting jobPosting = JobPosting.builder()
                            .title(title.isEmpty() ? "정보 없음" : title)
                            .company(company.isEmpty() ? "정보 없음" : company)
                            .location(location.isEmpty() ? "정보 없음" : location)
                            .experience(experience.isEmpty() ? "정보 없음" : experience)
                            .salary(salary.isEmpty() ? "정보 없음" : salary)
                            .position(position.isEmpty() ? "정보 없음" : position)
                            .link(link)
                            .uniqueIdentifier(uniqueIdentifier)
                            .views(0)
                            .build();

                    newJobs.add(jobPosting);
                }
            }

            repository.saveAll(newJobs);
            size -= jobElements.size();
            currentPage++;
        }
    }

    public JobResponse getJobById(Long id) {
        JobPosting job = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setViews(job.getViews() + 1);
        repository.save(job);

        return toJobResponse(job);
    }

    private JobResponse toJobResponse(JobPosting job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .experience(job.getExperience())
                .salary(job.getSalary())
                .position(job.getPosition())
                .link(job.getLink())
                .views(job.getViews())
                .build();
    }
}
