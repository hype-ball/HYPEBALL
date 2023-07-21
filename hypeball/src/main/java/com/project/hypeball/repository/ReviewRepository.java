package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryInterface {

    Optional<Review> findById(Long reviewId);

    @Override
    void deleteById(Long reviewId);
}
