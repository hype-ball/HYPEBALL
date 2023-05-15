package com.project.hypeball.repository;

import com.project.hypeball.domain.ReviewPoint;
import com.project.hypeball.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewPointRepository extends JpaRepository<ReviewPoint, Long>{

    @Query("select p from ReviewPoint p left join fetch p.review where p.review.store.id = :storeId")
    List<ReviewPoint> findTags(@Param("storeId") Long storeId);
}
