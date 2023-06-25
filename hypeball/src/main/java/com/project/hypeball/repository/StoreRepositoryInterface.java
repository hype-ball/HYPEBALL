package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerRankDto;
import com.querydsl.core.Tuple;

import java.util.List;

public interface StoreRepositoryInterface {
    List<Store> findAllFetch();
    List<MarkerRankDto> findRanksByStar(int limit);
}
