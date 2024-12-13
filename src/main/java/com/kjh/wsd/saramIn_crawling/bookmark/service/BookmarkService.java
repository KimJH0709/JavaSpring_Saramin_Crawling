package com.kjh.wsd.saramIn_crawling.bookmark.service;

import com.kjh.wsd.saramIn_crawling.auth.security.JwtUtil;
import com.kjh.wsd.saramIn_crawling.bookmark.model.Bookmark;
import com.kjh.wsd.saramIn_crawling.bookmark.repository.BookmarkRepository;
import com.kjh.wsd.saramIn_crawling.job.model.Job;
import com.kjh.wsd.saramIn_crawling.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final JobRepository jobRepository;
    private final JwtUtil jwtUtil;

    // 북마크 추가 또는 삭제
    public String toggleBookmark(Long jobId, String token) {
        String username = jwtUtil.extractUsername(token);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUsernameAndJob(username, job);

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            return "Unbookmarked";
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .job(job)
                    .username(username)
                    .build();
            bookmarkRepository.save(bookmark);
            return "Bookmarked";
        }
    }

    // 사용자의 북마크 목록 조회
    public Page<Bookmark> getBookmarks(Pageable pageable, String token) {
        String username = jwtUtil.extractUsername(token);

        return bookmarkRepository.findByUsername(username, pageable);
    }
}
