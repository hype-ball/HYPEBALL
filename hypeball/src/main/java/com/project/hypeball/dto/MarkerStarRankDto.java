package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;

@Data
public class MarkerStarRankDto {

    Long storeId;
    String name;
    String address;
    Double lat;
    Double lng;
    Double starAvg;

    @QueryProjection
    public MarkerStarRankDto(Long storeId, String name, String address, Double lat, Double lng, Double starAvg) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.starAvg = starAvg;
    }

}
