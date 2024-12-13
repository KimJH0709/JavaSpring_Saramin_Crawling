package com.kjh.wsd.saramIn_crawling.bookmark.controller;

import com.kjh.wsd.saramIn_crawling.bookmark.model.Bookmark;
import com.kjh.wsd.saramIn_crawling.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
@Tag(name = "Bookmarks", description = "API for managing bookmarks")
public class BookmarkController {

    private final BookmarkService service;

    // 북마크 추가 또는 삭제
    @PostMapping("/{jobId}")
    @Operation(summary = "Toggle bookmark", description = "Add or remove a bookmark for the specified job ID.")
    public ResponseEntity<String> toggleBookmark(@PathVariable Long jobId) {
        String result = service.toggleBookmark(jobId);
        return ResponseEntity.ok(result);
    }

    // 북마크 목록 조회
    @GetMapping
    @Operation(summary = "Get all bookmarks", description = "Retrieve a paginated list of bookmarks for the authenticated user.")
    public ResponseEntity<Page<Bookmark>> getBookmarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Bookmark> bookmarks = service.getBookmarks(pageable);
        return ResponseEntity.ok(bookmarks);
    }
}
