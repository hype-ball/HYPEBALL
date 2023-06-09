package com.project.hypeball.dto;

import lombok.Data;

@Data
public class StoreLikeDto {

    private Long storeLikeId;
    private Long storeId;
    private String storeName;
    private String storeAddress;

    public StoreLikeDto(Long storeLikeId, Long storeId, String storeName, String storeAddress) {
        this.storeLikeId = storeLikeId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }
}
