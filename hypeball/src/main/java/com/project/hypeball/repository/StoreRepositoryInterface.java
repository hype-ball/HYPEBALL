package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;

import java.util.List;

public interface StoreRepositoryInterface {
    List<Store> findAllFetch();
}
