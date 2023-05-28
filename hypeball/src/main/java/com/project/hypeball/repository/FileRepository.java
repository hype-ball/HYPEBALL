package com.project.hypeball.repository;

import com.project.hypeball.domain.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<AttachedFile, Long> {

    @Query("select f from AttachedFile f where f.review.id in :reviewList")
    List<AttachedFile> findAllByStoreId(@Param("reviewList") List<Long> reviewList);

    @Query("select f from AttachedFile f where f.review.id = :reviewId")
    List<AttachedFile> findAllByReviewId(@Param("reviewId") Long reviewId);
}
