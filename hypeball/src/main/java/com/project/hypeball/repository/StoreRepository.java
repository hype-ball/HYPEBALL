package com.project.hypeball.repository;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.CountDto;
import com.project.hypeball.dto.MarkerCardDto;
import com.project.hypeball.dto.StatisticDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryInterface{

    @EntityGraph(attributePaths = {"category", "starRating"})
    Optional<Store> findFetchById(Long storeId);

    @EntityGraph(attributePaths = {"category", "starRating"})
    List<Store> findByNameContainsOrAddressContains(String name, String address);

    @Query(value = "select new com.project.hypeball.dto.MarkerCardDto(s.id, s.name, s.address, s.lat, s.lng, sr.starAvg, s.totalLikeCount, count(r))" +
            " from Store s" +
            " join StarRating sr on s.starRating.id = sr.id" +
            " left join Review r on s.id = r.store.id" +
            " where s.name like %:keyword% or s.address like %:keyword%" +
            " group by s.id")
    List<MarkerCardDto> search(@Param("keyword") String keyword);

    // manyLikeStore()와 같음.
    List<Store> findFirst10ByTotalLikeCountGreaterThanOrderByTotalLikeCountDesc(int totalLikeCount);
    // JPQL + pageable 사용
    @Query(value = "select new com.project.hypeball.dto.StatisticDto(s.id, s.name, s.totalLikeCount)" +
            " from Store s where s.totalLikeCount > 0 order by s.totalLikeCount DESC")
    List<StatisticDto> manyLikeStore(Pageable pageable);

    @Query(value = "select new com.project.hypeball.dto.MarkerCardDto(s.id, s.name, s.address, s.lat, s.lng, sr.starAvg, s.totalLikeCount, count(r))" +
            " from Store s" +
            " join StarRating sr on s.starRating.id = sr.id" +
            " left join Review r on s.id = r.store.id" +
            " where s.totalLikeCount > 0" +
            " group by s.id" +
            " order by s.totalLikeCount DESC")
    List<MarkerCardDto> findRanksByLike(Pageable pageable);

    // native query 사용
    @Query(value = "select COUNT(*) as count, r.store_id as storeId, s.name as name" +
            " from store s" +
            " join Review r on s.store_id = r.store_id" +
            " group by r.store_id" +
            " order by 1 DESC LIMIT 10", nativeQuery = true)
    List<CountDto> manyReviewStore();

    @Query(value = "select new com.project.hypeball.dto.MarkerCardDto(s.id, s.name, s.address, s.lat, s.lng, sr.starAvg, s.totalLikeCount, count(r))" +
            " from Store s" +
            " join StarRating sr on s.starRating.id = sr.id" +
            " join Review r on s.id = r.store.id" +
            " group by r.store.id" +
            " order by count(r) DESC")
    List<MarkerCardDto> findRanksByReview(Pageable pageable);

    @Query(value = "select new com.project.hypeball.dto.StatisticDto(s.id, s.name, sr.starAvg)" +
            " from Store s join StarRating sr on s.starRating.id = sr.id where s.starRating.starAvg > 0 order by sr.starAvg DESC")
    List<StatisticDto> highStarStore(Pageable pageable);

    @Query(value = "select new com.project.hypeball.dto.MarkerCardDto(s.id, s.name, s.address, s.lat, s.lng, sr.starAvg, s.totalLikeCount, count(r))" +
            " from Store s" +
            " join StarRating sr on s.starRating.id = sr.id" +
            " left join Review r on s.id = r.store.id" +
            " group by s.id" +
            " order by s.id DESC")
    List<MarkerCardDto> findRanksByNew(Pageable pageable);

    List<Store> findByAddressContains(String address);

}