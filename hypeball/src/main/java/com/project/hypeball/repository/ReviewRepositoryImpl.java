package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
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


}
