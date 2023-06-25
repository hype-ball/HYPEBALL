package com.project.hypeball.service;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.CountDto;
import com.project.hypeball.dto.MarkerRankDto;
import com.project.hypeball.dto.StatisticDto;
import com.project.hypeball.repository.ReviewRepository;
import com.project.hypeball.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticQueryService {

    private final StoreRepository storeRepository;

    public List<CountDto> reviewManyStore() {
        return storeRepository.manyReviewStore();
    }
    public List<MarkerRankDto> findRanksByReview() {
        return storeRepository.findRanksByReview(PageRequest.of(0, 10));
    }

    public List<StatisticDto> likeManyStore() {
        return storeRepository.manyLikeStore(PageRequest.of(0, 10));
    }

    public List<Store> likeManyStore_method() {
        return storeRepository.findFirst10ByTotalLikeCountGreaterThanOrderByTotalLikeCountDesc(0);
    }

    public List<MarkerRankDto> findRanksByLike() {
        return storeRepository.findRanksByLike(PageRequest.of(0, 10));
    }

    public List<StatisticDto> highStarStore() {
        return storeRepository.highStarStore(PageRequest.of(0, 10));
    }

    public List<Store> search(String keyword) {
        return storeRepository.findByNameContainsOrAddressContains(keyword, keyword);
    }
}
