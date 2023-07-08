package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerCardDto;

import java.util.List;

public interface StoreRepositoryInterface {
    List<Store> findAllFetch();
    List<MarkerCardDto> findRanksByStar(int limit);
}
