package com.project.hypeball.repository;

import com.project.hypeball.domain.Point;
import com.project.hypeball.domain.ReviewPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewPointRepositoryImpl implements ReviewPointRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
  public ReviewPoint get(Long reviewPointId) {
    return em.find(ReviewPoint.class, reviewPointId);
  }

}
