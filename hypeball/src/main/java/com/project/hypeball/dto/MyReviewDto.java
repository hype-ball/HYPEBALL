package com.project.hypeball.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MyReviewDto {

    private Long reviewId;

    @NotNull
    private String storeName;

    @NotNull
    private String content; // 리뷰내용

    @NotNull
    private String createdDate; // 작성일

    @NotNull
    private Double star; // 별점

//    @NotNull
//    private String writer;

    private List<ReviewDrinkQueryDto> drinks = new ArrayList<>();

    private List<AttachedFileQueryDto> attachedFiles = new ArrayList<>();

    @QueryProjection
    public MyReviewDto(Long id, String content, String createdDate, Double star, String storeName) {
        this.reviewId = id;
        this.content = content;
        this.createdDate = createdDate;
        this.star = star;
        this.storeName = storeName;
    }
}
