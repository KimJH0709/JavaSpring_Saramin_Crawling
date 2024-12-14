package com.kjh.wsd.saramIn_crawling.job.specification;

import com.kjh.wsd.saramIn_crawling.job.model.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> containsTitle(String title) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsCompany(String company) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("company")), "%" + company.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsLocation(String location) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("location")), "%" + location.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsExperience(String experience) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("experience")), "%" + experience.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsSalary(String salary) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("salary")), "%" + salary.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsEmploymentType(String employmentType) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("employmentType")), "%" + employmentType.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsRequirements(String requirements) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("requirements")), "%" + requirements.toLowerCase() + "%"
        );
    }

    public static Specification<Job> containsSector(String sector) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("sector")), "%" + sector.toLowerCase() + "%"
        );
    }
}
