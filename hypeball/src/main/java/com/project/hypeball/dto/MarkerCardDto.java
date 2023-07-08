package com.project.hypeball.dto;

import com.project.hypeball.domain.Store;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MarkerCardDto {

    Long storeId;
    String name;
    String address;
    Double lat;
    Double lng;
    Double starAvg;
    int likeCount;
    long reviewCount;

    @QueryProjection
    public MarkerCardDto(Long storeId, String name, String address, Double lat, Double lng, Double starAvg, int likeCount, long reviewCount) {
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
