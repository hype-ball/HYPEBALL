package com.project.hypeball.repository;

import com.project.hypeball.dto.PointCountDto;

import java.util.List;

public interface ReviewPointRepositoryInterface {

    List<PointCountDto> pointCount(Long storeId);
    Long deleteReviewPoint(Long reviewId);

}
