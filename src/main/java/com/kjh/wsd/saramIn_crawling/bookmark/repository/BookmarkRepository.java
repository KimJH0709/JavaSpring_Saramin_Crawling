package com.kjh.wsd.saramIn_crawling.bookmark.repository;

import com.kjh.wsd.saramIn_crawling.bookmark.model.Bookmark;
import com.kjh.wsd.saramIn_crawling.job.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT b FROM Bookmark b WHERE b.username = :username")
    Page<Bookmark> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT b FROM Bookmark b WHERE b.username = :username AND b.job = :job")
    Optional<Bookmark> findByUsernameAndJob(@Param("username") String username, @Param("job") Job job);
}
