package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

@BatchSize(size = 100)
@Data
public class AttachedFileQueryDto {

    private Long reviewId;
    private String storeFileName;

    public AttachedFileQueryDto() {
    }

    @QueryProjection
    public AttachedFileQueryDto(Long reviewId, String storeFileName) {
        this.reviewId = reviewId;
        this.storeFileName = storeFileName;
    }
}
