package com.project.hypeball.repository;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.PointCountDto;
import com.project.hypeball.dto.QPointCountDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.hypeball.domain.QPoint.*;
import static com.project.hypeball.domain.QReview.*;
import static com.project.hypeball.domain.QReviewPoint.*;

@Repository
@RequiredArgsConstructor
public class ReviewPointRepositoryImpl implements ReviewPointRepositoryInterface {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;


  public ReviewPoint get(Long reviewPointId) {
    return em.find(ReviewPoint.class, reviewPointId);
  }

  @Override
  public List<PointCountDto> pointCount(Long storeId) {
    List<PointCountDto> result = queryFactory
            .select(new QPointCountDto(point.name, point.count()))
            .from(review)
            .join(reviewPoint).on(review.id.eq(reviewPoint.review.id))
            .join(point).on(reviewPoint.point.id.eq(point.id))
            .where(review.store.id.eq(storeId))
            .groupBy(point.name)
            .orderBy(point.count().desc())
            .fetch();

    return result;
  }
}
