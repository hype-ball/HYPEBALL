package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PointCountDto {

    private String pointName; // 분위기 태그
    private Long count; // 추천 횟수

    @QueryProjection
    public PointCountDto(String pointName, Long count) {
        this.pointName = pointName;
        this.count = count;
    }
}
