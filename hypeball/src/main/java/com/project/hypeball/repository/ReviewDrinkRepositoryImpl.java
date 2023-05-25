package com.project.hypeball.repository;

import com.project.hypeball.dto.DrinkCountDto;
import com.project.hypeball.dto.QDrinkCountDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.hypeball.domain.QReview.*;
import static com.project.hypeball.domain.QReviewDrink.*;

@Repository
public class ReviewDrinkRepositoryImpl implements ReviewDrinkRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewDrinkRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<DrinkCountDto> drinkCount(Long storeId) {

        List<DrinkCountDto> result = queryFactory
                .select(new QDrinkCountDto(reviewDrink.drink, reviewDrink.count()))
                .from(reviewDrink)
                .join(review)
                .on(reviewDrink.review.id.eq(review.id))
                .where(review.store.id.eq(storeId))
                .groupBy(reviewDrink.drink)
                .orderBy(reviewDrink.count().desc())
                .fetch();

        return result;

    }
}
