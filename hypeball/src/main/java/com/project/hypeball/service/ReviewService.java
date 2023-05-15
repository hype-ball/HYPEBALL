package com.project.hypeball.service;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.ReviewUpdateForm;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import com.project.hypeball.repository.ReviewDrinkRepository;
import com.project.hypeball.repository.ReviewPointRepository;
import com.project.hypeball.repository.ReviewRepository;
import com.project.hypeball.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewPointRepository reviewPointRepository;
    private final ReviewDrinkRepository reviewDrinkRepository;

    public List<Review> getReviewsByStore(Store store) {
//        return reviewRepository.findByStore(store);
        return reviewRepository.findReviewsFetch(store);
    }

    // 리뷰 저장
    @Transactional
    public Review save(Map<String, Object> param, Store store, Member member) {
        Review review = Review.createReview(param, store, member);
        return reviewRepository.save(review);
    }

    // 분위기 태그 저장
    public void reviewPointSave(Review review, Point point) {
        ReviewPoint reviewPoint = ReviewPoint.createReviewPoint(review, point);
        reviewPointRepository.save(reviewPoint);
    }

    public List<ReviewPoint> reviewPoints(Long storeId) {
        return reviewPointRepository.findTags(storeId);
    }


    // 술 태그 저장
    public void reviewDrinkSave(Review review, String drink) {
        ReviewDrink reviewDrink = ReviewDrink.createReviewDrink(review, drink);
        reviewDrinkRepository.save(reviewDrink);
    }

    public Review get(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(Long id) {
        reviewRepository.delete(get(id));
    }

}
