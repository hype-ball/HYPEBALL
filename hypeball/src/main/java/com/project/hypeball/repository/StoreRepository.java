package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.CountDto;
import com.project.hypeball.dto.StatisticDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryInterface{

    @EntityGraph(attributePaths = {"category", "starRating"})
    Optional<Store> findFetchById(Long storeId);

    List<Store> findByNameContainsOrAddressContains(String name, String address);

    // manyLikeStore()와 같음.
    List<Store> findFirst10ByTotalLikeCountGreaterThanOrderByTotalLikeCountDesc(int totalLikeCount);
    // JPQL + pageable 사용
    @Query(value = "select new com.project.hypeball.dto.StatisticDto(s.id, s.name, s.totalLikeCount)" +
            " from Store s where s.totalLikeCount > 0 order by s.totalLikeCount DESC")
    List<StatisticDto> manyLikeStore(Pageable pageable);

    // native query 사용
    @Query(value = "select COUNT(*) as count, r.store_id as storeId, s.name as name" +
            " from store s" +
            " join Review r on s.store_id = r.store_id" +
            " group by r.store_id" +
            " order by 1 DESC LIMIT 10", nativeQuery = true)
    List<CountDto> manyReviewStore();

    @Query(value = "select new com.project.hypeball.dto.StatisticDto(s.id, s.name, sr.starAvg)" +
            " from Store s join s.starRating sr on s.starRating.id = sr.id where s.starRating.starAvg > 0 order by sr.starAvg DESC")
    List<StatisticDto> highStarStore(Pageable pageable);

}