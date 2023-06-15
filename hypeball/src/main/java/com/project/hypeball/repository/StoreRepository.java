package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryInterface{

    @EntityGraph(attributePaths = {"category", "starRating"})
    Optional<Store> findFetchById(Long storeId);
}