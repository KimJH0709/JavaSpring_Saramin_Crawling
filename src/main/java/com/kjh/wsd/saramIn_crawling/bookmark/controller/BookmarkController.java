package com.kjh.wsd.saramIn_crawling.bookmark.controller;

import com.kjh.wsd.saramIn_crawling.bookmark.model.Bookmark;
import com.kjh.wsd.saramIn_crawling.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService service;

    // 북마크 추가 또는 삭제
    @PostMapping("/{jobId}")
    public ResponseEntity<String> toggleBookmark(@PathVariable Long jobId) {
        String result = service.toggleBookmark(jobId);
        return ResponseEntity.ok(result);
    }

    // 북마크 목록 조회
    @GetMapping
    public ResponseEntity<Page<Bookmark>> getBookmarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Bookmark> bookmarks = service.getBookmarks(pageable);
        return ResponseEntity.ok(bookmarks);
    }
}
