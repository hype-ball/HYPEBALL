package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
  public Store get(Long storeId) {
    return em.find(Store.class, storeId);
  }
}
