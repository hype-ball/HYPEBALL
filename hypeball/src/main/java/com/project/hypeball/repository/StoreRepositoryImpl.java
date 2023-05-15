package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;
  public Store get(Long storeId) {
    return em.find(Store.class, storeId);
  }

  public List<Store> findAllFetch() {

    List resultList = em.createQuery("select s from Store s" +
            " join fetch s.category c", Store.class
    ).getResultList();

    return resultList;
  }
}
