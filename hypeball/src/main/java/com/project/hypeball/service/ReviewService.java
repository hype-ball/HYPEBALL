package com.project.hypeball.service;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.ReviewSaveForm;
import com.project.hypeball.dto.ReviewUpdateForm;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import com.project.hypeball.repository.ReviewRepository;
import com.project.hypeball.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getReviewsByStore(Store store) {
        return reviewRepository.findByStore(store);
    }

    @Transactional
    public Review add(ReviewSaveForm form) {
        return reviewRepository.save(Review.createReview(form));
    }

    public Review get(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }



//    @Transactional
//    public void update(ReviewUpdateForm form) {
//
//        Review review = get(form.getId());
//        review.updateReview(review, form);
//    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.delete(get(id));
    }


}
