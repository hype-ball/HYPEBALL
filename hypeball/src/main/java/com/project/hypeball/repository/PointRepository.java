package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryInterface{

}
