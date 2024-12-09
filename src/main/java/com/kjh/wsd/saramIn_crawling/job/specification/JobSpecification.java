package com.kjh.wsd.saramIn_crawling.job.specification;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> containsTitle(String title) {
        return (root, query, builder) -> title == null || title.isEmpty() ? null :
                builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Job> containsCompany(String company) {
        return (root, query, builder) -> company == null || company.isEmpty() ? null :
                builder.like(root.get("company"), "%" + company + "%");
    }

    public static Specification<Job> containsLocation(String location) {
        return (root, query, builder) -> location == null || location.isEmpty() ? null :
                builder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<Job> containsExperience(String experience) {
        return (root, query, builder) -> experience == null || experience.isEmpty() ? null :
                builder.like(root.get("experience"), "%" + experience + "%");
    }

    public static Specification<Job> containsSalary(String salary) {
        return (root, query, builder) -> salary == null || salary.isEmpty() ? null :
                builder.like(root.get("salary"), "%" + salary + "%");
    }
}
