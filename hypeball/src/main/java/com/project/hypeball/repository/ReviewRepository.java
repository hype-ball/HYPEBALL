package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryInterface {

//    List<ReviewDto> findReviewsByMember_Id(Long memberId);
    Optional<Review> findById(Long reviewId);

    @Override
    void deleteById(Long reviewId);
}
