package com.project.hypeball.repository;

import com.project.hypeball.domain.QReviewPoint;
import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.ReviewPoint;
import com.project.hypeball.dto.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;

import static com.project.hypeball.domain.QAttachedFile.*;
import static com.project.hypeball.domain.QMember.*;
import static com.project.hypeball.domain.QReview.*;
import static com.project.hypeball.domain.QReviewDrink.reviewDrink;
import static com.project.hypeball.domain.QReviewPoint.*;
import static com.project.hypeball.domain.QStore.store;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryInterface {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Review get(Long reviewId) {
        return em.find(Review.class, reviewId);
    }

    @Override
    public Page<ReviewDto> findReviewsPaging(Long storeId, String sort, Pageable pageable) {

        System.out.println("ReviewRepositoryImpl.findReviewsPaging");

        List<ReviewDto> content = queryFactory
                .select(new QReviewDto(review.id, review.content, review.createdDate, review.star, review.member.name))
                .from(review)
                .join(review.member, member)
                .where(review.store.id.eq(storeId))
                .orderBy(sortCriteria(sort).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> ids = toReviewIds(content);
        if (!ids.isEmpty()) {
            Map<Long, List<AttachedFileQueryDto>> filesMap = findAttachedFileMap(ids);
            Map<Long, List<ReviewDrinkQueryDto>> drinksMap = findReviewDrink(ids);
            System.out.println("filesMap = " + filesMap);

            content.forEach(o -> {
                o.setAttachedFiles(filesMap.get(o.getReviewId()));
                o.setDrinks(drinksMap.get(o.getReviewId()));
                System.out.println("o.getAttachedFiles() = " + o.getAttachedFiles());
            });
        }

      JPAQuery<Long> countQuery = queryFactory
              .select(review.count())
              .from(review)
              .where(review.store.id.eq(storeId));

      return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

    @Override
    public List<MyReviewDto> findReviewsByMember(Long memberId, String sort) {

        System.out.println("ReviewRepositoryImpl.findReviewsByMember");

        List<MyReviewDto> myReviews = queryFactory
                .select(new QMyReviewDto(review.id, review.store.id, review.content, review.createdDate, review.star, review.store.name))
                .from(review)
                .join(review.member, member)
                .join(review.store, store)
                .where(review.member.id.eq(memberId))
                .orderBy(sortCriteria(sort).toArray(OrderSpecifier[]::new))
                .fetch();


        List<Long> ids = toMyReviewIds(myReviews);
        if (!ids.isEmpty()) {
            Map<Long, List<AttachedFileQueryDto>> filesMap = findAttachedFileMap(ids);
            Map<Long, List<ReviewDrinkQueryDto>> drinksMap = findReviewDrink(ids);
            System.out.println("filesMap = " + filesMap);

            myReviews.forEach(o -> {
                o.setAttachedFiles(filesMap.get(o.getReviewId()));
                o.setDrinks(drinksMap.get(o.getReviewId()));
                System.out.println("o.getAttachedFiles() = " + o.getAttachedFiles());
            });
        }

        return myReviews;
    }


    private Map<Long, List<AttachedFileQueryDto>> findAttachedFileMap(List<Long> reviewIds) {

    List<AttachedFileQueryDto> result = queryFactory
            .select(new QAttachedFileQueryDto(attachedFile.review.id, attachedFile.storeFileName))
            .from(attachedFile)
            .where(review.id.in(reviewIds))
            .fetch();

    return result.stream()
            .collect(Collectors.groupingBy(AttachedFileQueryDto::getReviewId));
  }


    private Map<Long, List<ReviewDrinkQueryDto>> findReviewDrink(List<Long> reviewIds) {

        List<ReviewDrinkQueryDto> result = queryFactory
                .select(new QReviewDrinkQueryDto(reviewDrink.review.id, reviewDrink.drink))
                .from(reviewDrink)
                .where(reviewDrink.review.id.in(reviewIds))
                .fetch();

        return result.stream()
                .collect(Collectors.groupingBy(ReviewDrinkQueryDto::getReviewId));
    }

    private List<Long> toReviewIds(List<ReviewDto> content) {
    return content.stream()
            .map(ReviewDto::getReviewId)
            .collect(Collectors.toList());
  }

    private List<Long> toMyReviewIds(List<MyReviewDto> content) {
        return content.stream()
                .map(MyReviewDto::getReviewId)
                .collect(Collectors.toList());
    }

    private List<OrderSpecifier<?>> sortCriteria(String sort) {
        List<OrderSpecifier<?>> specifiers = new ArrayList<>();

        switch (sort) {
            case "starAsc" -> {
                specifiers.add(review.star.asc());
                specifiers.add(review.createdDate.desc());
            }
            case "starDesc" -> {
                specifiers.add(review.star.desc());
                specifiers.add(review.createdDate.desc());
            }
            case "cdtAsc" -> specifiers.add(review.createdDate.asc());
            default -> specifiers.add(review.createdDate.desc());
        }

        return specifiers;
    }

    public Long deleteReview(Long reviewId) {

        long delReviewCnt = queryFactory
                .delete(review)
                .where(review.id.eq(reviewId))
                .execute();

        return delReviewCnt;
    }

}
