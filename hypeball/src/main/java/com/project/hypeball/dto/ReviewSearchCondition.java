package com.project.hypeball.dto;

import lombok.Data;

@Data
public class ReviewSearchCondition {
    // 가게 id, 정렬기준
    private Long storeId;
    // 최신순, 별점순
    private String sort;
}
