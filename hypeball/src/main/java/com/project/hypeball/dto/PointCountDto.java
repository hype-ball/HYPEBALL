package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PointCountDto {

    private String pointName; // 추천 하이볼 이름
    private Long count; // 추천 횟수

    public PointCountDto() {
    }

    @QueryProjection
    public PointCountDto(String pointName, Long count) {
        this.pointName = pointName;
        this.count = count;
    }
}
