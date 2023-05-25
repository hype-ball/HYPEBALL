package com.project.hypeball.dto;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Store;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter @Setter
public class ReviewDto {

    @NotNull
    private String content; // 리뷰내용

    @NotNull
    private String createdDate; // 작성일

    @NotNull
    private Double star; // 별점

    @NotNull
    private String writer;

    public ReviewDto() {
    }

    @QueryProjection
    public ReviewDto(String content, String createdDate, Double star, String writer) {
        this.content = content;
        this.createdDate = createdDate;
        this.star = star;
        this.writer = writer;
    }
}
