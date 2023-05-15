package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryInterface{

//    List<Store> findAllFetch();
}