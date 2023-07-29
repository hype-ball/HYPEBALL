package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryInterface {

    @Override
    void deleteById(Long reviewId);
}
