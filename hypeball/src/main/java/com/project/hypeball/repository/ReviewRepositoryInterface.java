package com.project.hypeball.repository;

import com.project.hypeball.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryInterface {

    Page <ReviewDto> findReviewsPaging(Long storeId, String sort, Pageable pageable);
}
