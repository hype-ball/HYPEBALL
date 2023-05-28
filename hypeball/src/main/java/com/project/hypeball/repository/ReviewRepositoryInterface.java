package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryInterface {

    public List<Review> findReviewsFetch(Store store);
    Page <ReviewDto> findReviewsPaging(Long storeId, String sort, Pageable pageable);
    Page<ReviewDto> findAllStoreId(Long storeId, String sort, Pageable pageable);
}
