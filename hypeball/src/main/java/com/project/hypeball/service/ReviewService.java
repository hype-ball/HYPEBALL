package com.project.hypeball.service;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.*;
import com.project.hypeball.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewPointRepository reviewPointRepository;
    private final ReviewDrinkRepository reviewDrinkRepository;
    private final PointRepository pointRepository;

    private final FileStore fileStore;
    private final FileRepository fileRepository;

    public List<Review> getReviewsByStore(Store store) {
//        return reviewRepository.findByStore(store);
        return reviewRepository.findReviewsFetch(store);
    }

    // 리뷰 저장
    @Transactional
    public Review save(ReviewAddDto reviewAddDto, Store store, Member member, List<AttachedFile> attachedFiles) {

        // 리뷰 저장
        Review review = reviewRepository.save(Review.createReview(reviewAddDto, store, member));
        Store.saveReviewToStore(store, review);

        // 분위기 태그 저장
        List<Point> pointList = pointRepository.findPointList(reviewAddDto.getPointList());
        for (Point point : pointList) {
            ReviewPoint reviewPoint = ReviewPoint.createReviewPoint(review, point);
            reviewPointRepository.save(reviewPoint);
        }

        // 술 태그 저장
        for (String drink : reviewAddDto.getDrinkList()) {
            ReviewDrink reviewDrink = ReviewDrink.createReviewDrink(review, drink);
            reviewDrinkRepository.save(reviewDrink);
        }

        if (attachedFiles != null) {
            review.setImageFiles(attachedFiles);
            // 파일 db 저장
            for (AttachedFile attachedFile : attachedFiles) {
                attachedFile.setReview(review);
                fileRepository.save(attachedFile);
            }
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
    public Page<ReviewDto> reviewsPaging(Long storeId, String sort, Pageable pageable) {
        return reviewRepository.findReviewsPaging(storeId, sort, pageable);
    }

//    public Page<ReviewDto> findAllStoreId(Long storeId, String sort, Pageable pageable) {
//        return reviewRepository.findAllStoreId(storeId, sort, pageable);
//    }
    @Transactional
    public void delete(Long id) {
        reviewRepository.delete(get(id));
    }

}
