package com.project.hypeball.repository;

import com.project.hypeball.domain.QStore;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerRankDto;
import com.project.hypeball.dto.QMarkerRankDto;
import com.querydsl.core.Tuple;
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
  public List<MarkerRankDto> findRanksByStar(int limit) {

    List<MarkerRankDto> starRankDtos = queryFactory
            .select(new QMarkerRankDto(store.id, store.name, store.address, store.lat, store.lng, store.starRating.starAvg, store.totalLikeCount))
            .from(store)
            .where(store.starRating.starAvg.gt(0))
            .orderBy(store.starRating.starAvg.desc())
            .limit(limit)
            .fetch();

    return starRankDtos;
  }
}
