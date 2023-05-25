package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class DrinkCountDto {

    private String drinkName; // 추천 하이볼 이름
    private Long count; // 추천 횟수

    public DrinkCountDto() {
    }

    @QueryProjection
    public DrinkCountDto(String drinkName, Long count) {
        this.drinkName = drinkName;
        this.count = count;
    }

}
