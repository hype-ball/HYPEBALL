package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryInterface{
    List<Review> findByStore(Store store);

}
