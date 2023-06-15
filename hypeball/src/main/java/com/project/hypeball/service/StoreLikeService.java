package com.project.hypeball.service;

import com.project.hypeball.domain.*;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.dto.ReviewAddDto;
import com.project.hypeball.dto.StoreLikeDto;
import com.project.hypeball.repository.StoreLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreLikeService {

    private final StoreLikeRepository storeLikeRepository;

    public List<StoreLikeDto> findByMember(Member member) {
        List<StoreLike> allByMemberId = storeLikeRepository.findAllByMemberId(member.getId());
        return allByMemberId.stream().map(l -> new StoreLikeDto(l.getId(), l.getStore().getId(),
                        l.getStore().getName(), l.getStore().getAddress()))
                .collect(Collectors.toList());
    }

    public List<MarkerDto> findMarkerByMember(Member member) {
        List<StoreLike> allByMemberId = storeLikeRepository.findAllByMemberId(member.getId());
        return allByMemberId.stream().map(l -> new MarkerDto(l.getStore().getId(), l.getStore().getName(), l.getStore().getLat(), l.getStore().getLng()))
                .collect(Collectors.toList());
    }

    @Transactional
    public int save(Store store, Member member) {

        storeLikeRepository.save(StoreLike.createStoreLike(store, member));
        int totalLikeCount = Store.addCount(store);

        return totalLikeCount;
    }

    @Transactional
    public int delete(Store store, Member member) throws Exception {

        StoreLike storeLike = storeLikeRepository.findByStoreIdAndMemberId(store.getId(), member.getId());
        storeLikeRepository.delete(storeLike);
        int totalLikeCount = Store.removeCount(store);

        return totalLikeCount;
    }

    public StoreLike get(Long storeId, Long memberId) {
        return storeLikeRepository.findByStoreIdAndMemberId(storeId, memberId);
    }
}
