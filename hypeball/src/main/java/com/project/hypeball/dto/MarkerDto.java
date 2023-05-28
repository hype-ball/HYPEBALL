package com.project.hypeball.dto;

import lombok.Data;

@Data
public class MarkerDto {

    Long storeId;
    String name;
    Double lat;
    Double lng;

    public MarkerDto(Long storeId, String name, Double lat, Double lng) {
        this.storeId = storeId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
