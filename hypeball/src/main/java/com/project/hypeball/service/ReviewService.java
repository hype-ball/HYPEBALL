package com.project.hypeball.service;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.*;
import com.project.hypeball.repository.*;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PointRepository pointRepository;

    public List<Review> getReviewsByStore(Store store) {
//        return reviewRepository.findByStore(store);
        return reviewRepository.findReviewsFetch(store);
    }

    // 리뷰 저장
    @Transactional
    public Review save(Map<String, Object> param, Store store, Member member, List<Long> pointList, List<String> drinkList) {
        // 리뷰 저장
        Review review = reviewRepository.save(Review.createReview(param, store, member));
        Store.saveReviewToStore(store, review);

        // 분위기 태그 저장
        for (Long pointId : pointList) {
            ReviewPoint reviewPoint = ReviewPoint.createReviewPoint(review, pointRepository.get(pointId));
            reviewPointRepository.save(reviewPoint);
        }

        // 술 태그 저장
        for (String drink : drinkList) {
            ReviewDrink reviewDrink = ReviewDrink.createReviewDrink(review, drink);
            reviewDrinkRepository.save(reviewDrink);
        }

        return review;
    }

    public List<ReviewPoint> reviewPoints(Long storeId) {
        return reviewPointRepository.findTags(storeId);
    }

    public Review get(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // 하이볼태그 태그명,count 가져오기
    public List<DrinkCountDto> drinkTagsRank(Long storeId) {
        return reviewDrinkRepository.drinkCount(storeId);
    }

    // 포인트태그 태그명,count 가져오기
    public List<PointCountDto> pointTagsRank(Long storeId) {return reviewPointRepository.pointCount(storeId);}

    // 리뷰 페이징
    public Page<ReviewDto> reviewsPaging(ReviewSearchCondition condition, Pageable pageable) {
        return reviewRepository.findReviewsPaging(condition, pageable);
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.delete(get(id));
    }

}
