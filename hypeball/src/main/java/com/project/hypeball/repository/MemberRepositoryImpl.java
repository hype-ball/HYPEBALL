package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
  public Member get(Long memberId) {
    return em.find(Member.class, memberId);
  }
}
