package com.project.hypeball.repository;

import com.project.hypeball.domain.StoreLike;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Long> {

    StoreLike findByStoreIdAndMemberId(Long storeId, Long memberId);

    @EntityGraph(attributePaths = {"store"})
    List<StoreLike> findAllByMemberId(Long memberId);
}
