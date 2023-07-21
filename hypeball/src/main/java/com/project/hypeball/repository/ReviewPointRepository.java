package com.project.hypeball.repository;

import com.project.hypeball.domain.ReviewPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewPointRepository extends JpaRepository<ReviewPoint, Long>, ReviewPointRepositoryInterface {

}
