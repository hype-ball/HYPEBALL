package com.project.hypeball.dto;

import com.project.hypeball.domain.AttachedFile;
import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Store;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewDto {

    @NotNull
    private Long id;

    @NotNull
    private String content; // 리뷰내용

    @NotNull
    private String createdDate; // 작성일

    @NotNull
    private Double star; // 별점

    @NotNull
    private String writer;

//    private List<AttachedFile> attachedFiles = new ArrayList<>();
//    @BatchSize(size = 100)
    private List<AttachedFileQueryDto> attachedFiles = new ArrayList<>();

    public ReviewDto() {
    }

//    @QueryProjection
//    public ReviewDto(String content, String createdDate, Double star, String writer, List<AttachedFileQueryDto> files) {
//        this.content = content;
//        this.createdDate = createdDate;
//        this.star = star;
//        this.writer = writer;
//        this.attachedFiles = files;
//    }

    @QueryProjection
    public ReviewDto(Long id,String content, String createdDate, Double star, String writer) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.star = star;
        this.writer = writer;
    }

}
