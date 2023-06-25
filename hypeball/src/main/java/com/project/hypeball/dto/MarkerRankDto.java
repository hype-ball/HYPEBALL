package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;

@Data
public class MarkerRankDto {

    Long storeId;
    String name;
    String address;
    Double lat;
    Double lng;
    Double starAvg;
    int likeCount;
    long reviewCount;

    @QueryProjection
    public MarkerRankDto(Long storeId, String name, String address, Double lat, Double lng, Double starAvg, int likeCount) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.starAvg = starAvg;
        this.likeCount = likeCount;

    }

    public MarkerRankDto(Long storeId, String name, String address, Double lat, Double lng, Double starAvg, int likeCount, long reviewCount) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.starAvg = starAvg;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
    }
}
