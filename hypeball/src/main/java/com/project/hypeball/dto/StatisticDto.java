package com.project.hypeball.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticDto {
    private Long storeId;
    private String name;
    private int likeCount;
    private Double star;

    public StatisticDto(Long storeId, String name, int likeCount) {
        this.storeId = storeId;
        this.name = name;
        this.likeCount = likeCount;
    }

    public StatisticDto(Long storeId, String name, Double star) {
        this.storeId = storeId;
        this.name = name;
        this.star = star;
    }
}
