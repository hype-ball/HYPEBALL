package com.project.hypeball.repository;

import com.project.hypeball.dto.MyReviewDto;
import com.project.hypeball.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.hypeball.domain.QReview.review;
import static com.project.hypeball.domain.QReviewDrink.reviewDrink;
import static com.project.hypeball.domain.QReviewPoint.reviewPoint;

public interface ReviewRepositoryInterface {

    List<MyReviewDto> findReviewsByMember(Long memberId, String sort);
    Page <ReviewDto> findReviewsPaging(Long storeId, String sort, Pageable pageable);
    Long deleteReview(Long reviewId);

}
