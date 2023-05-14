package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
  public Point get(Long pointId) {
    return em.find(Point.class, pointId);
  }

}
