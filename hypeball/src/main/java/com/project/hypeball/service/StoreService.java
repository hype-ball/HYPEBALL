package com.project.hypeball.service;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import com.project.hypeball.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Transactional
    public Store add(StoreSaveForm form) {
        return storeRepository.save(Store.createStore(form));
    }

    public Store get(Long id) {
        return storeRepository.findById(id).orElse(null);
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
}
