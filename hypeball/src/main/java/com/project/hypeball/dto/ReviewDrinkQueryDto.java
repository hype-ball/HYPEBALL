package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ReviewDrinkQueryDto {

    private Long reviewId;
    private String drink;

    @QueryProjection
    public ReviewDrinkQueryDto(Long reviewId, String drink) {
        this.reviewId = reviewId;
        this.drink = drink;
    }
}
