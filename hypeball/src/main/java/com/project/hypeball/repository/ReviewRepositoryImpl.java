package com.project.hypeball.repository;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.FileDto;
import com.project.hypeball.dto.QReviewDto;
import com.project.hypeball.dto.ReviewDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.hypeball.domain.QCategory.*;
import static com.project.hypeball.domain.QMember.*;
import static com.project.hypeball.domain.QReview.*;
import static com.project.hypeball.domain.QStore.*;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryInterface {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Review get(Long reviewId) {
        return em.find(Review.class, reviewId);
    }

    public List<Review> findReviewsFetch(Store store) {
        List<Review> resultList = em.createQuery(
                        "select r from Review r " +
                                " join fetch r.store s" +
                                " where r.store.id=?1", Review.class)
                .setParameter(1, store.getId())
                .getResultList();

        return resultList;
    }

    @Override
    public Page<ReviewDto> findReviewsPaging(Long storeId, String sort, Pageable pageable) {

        List<ReviewDto> content = queryFactory
                .select(new QReviewDto(review.id, review.content, review.createdDate, review.star, review.member.name))
                .from(review)
                .join(review.member, member)
                .where(review.store.id.eq(storeId))
                .orderBy(sortCriteria(sort).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(review)
                .where(review.store.id.eq(storeId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReviewDto> findAllStoreId(Long storeId, String sort, Pageable pageable) {
        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.store, store).fetchJoin()
                .join(store.category, category).fetchJoin()
                .where(review.store.id.eq(storeId))
                .orderBy(sortCriteria(sort).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getContent(), r.getCreatedDate(), r.getStar(), r.getMember().getName(),
                        r.getImageFiles().stream().map(f -> new FileDto(f.getUploadFileName(), f.getStoreFileName())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        long total = queryFactory
                .selectFrom(review)
                .where(review.store.id.eq(storeId))
                .fetchCount();

        return new PageImpl<>(reviewDtos, pageable, total);
    }

    private List<OrderSpecifier<?>> sortCriteria(String sort) {
        List<OrderSpecifier<?>> specifiers = new ArrayList<>();

        switch (sort) {
            case "starAsc":
                specifiers.add(review.star.asc());
                specifiers.add(review.createdDate.desc());
                break;
            case "starDesc":
                specifiers.add(review.star.desc());
                specifiers.add(review.createdDate.desc());
                break;
            case "cdtAsc":
                specifiers.add(review.createdDate.asc());
                break;
            default:
                specifiers.add(review.createdDate.desc());
                break;
        }

        return specifiers;
    }

}
