package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.QAttachedFile;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.project.hypeball.domain.QAttachedFile.*;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  @Override
  public long deleteAttachedFileByReviewId(Long reviewId) {

    long delFileCnt = queryFactory
            .delete(attachedFile)
            .where(attachedFile.review.id.eq(reviewId))
            .execute();

    return delFileCnt;
  }
}
