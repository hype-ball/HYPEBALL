package com.project.hypeball.repository;

import com.project.hypeball.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryInterface{

    @Query("select p from Point p where p.id in :points")
    List<Point> findPointList(@Param("points") List<Long> pointList);

}
