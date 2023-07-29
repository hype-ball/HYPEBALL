package com.project.hypeball.service;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerCardDto;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import com.project.hypeball.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<Store> findAll() {
        return storeRepository.findAllFetch();
    }

    @Transactional
    public Store add(StoreSaveForm form) {
        return storeRepository.save(Store.createStore(form));
    }

    public Store get(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    public Store getFetch(Long id) {
        return storeRepository.findFetchById(id).orElse(null);
    }

    @Transactional
    public void update(StoreUpdateForm form) {
        Store store = get(form.getId());
        store.updateStore(store, form);
    }

    @Transactional
    public void delete(Long id) {
        storeRepository.delete(get(id));
    }

    public List<MarkerCardDto> findRanksByStar(int limit) {
        return storeRepository.findRanksByStar(limit);
    }

    public List<MarkerCardDto> findRanksByReview(int limit) {
        return storeRepository.findRanksByReview(PageRequest.of(0, limit));
    }

    public List<MarkerCardDto> findRanksByLike(int limit) {
        return storeRepository.findRanksByLike(PageRequest.of(0, limit));
    }

    public List<MarkerCardDto> findRanksByNew(int limit) {
        return storeRepository.findRanksByNew(PageRequest.of(0, limit));
    }

    public List<Store> findByRegion(String address) {return storeRepository.findByAddressContains(address);}

    public List<MarkerCardDto> search(String keyword) {
        return storeRepository.search(keyword);
    }
}
