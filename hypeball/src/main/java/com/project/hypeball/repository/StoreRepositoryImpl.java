package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerCardDto;
import com.project.hypeball.dto.QMarkerCardDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.hypeball.domain.QReview.*;
import static com.project.hypeball.domain.QStore.*;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryInterface {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  public Store get(Long storeId) {
    return em.find(Store.class, storeId);
  }

  public List<Store> findAllFetch() {

    List resultList = em.createQuery("select s from Store s" +
                    " join fetch s.category c" +
                    " join fetch s.starRating sr", Store.class)
            .getResultList();

    return resultList;
  }

  @Override
  public List<MarkerCardDto> findRanksByStar(int limit) {

    List<MarkerCardDto> starRankDtos = queryFactory
            .select(new QMarkerCardDto(store.id, store.name, store.address, store.lat, store.lng, store.starRating.starAvg, store.totalLikeCount, review.count()))
            .from(store)
            .leftJoin(review).on(review.store.id.eq(store.id))
            .where(store.starRating.starAvg.gt(0))
            .groupBy(store.id)
            .orderBy(store.starRating.starAvg.desc())
            .limit(limit)
            .fetch();

    return starRankDtos;
  }
}
