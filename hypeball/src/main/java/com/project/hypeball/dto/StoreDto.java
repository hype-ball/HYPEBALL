package com.project.hypeball.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hypeball.domain.Category;
import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.StarRating;
import com.project.hypeball.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreDto {

    private Long id;
    private String name; // 상호명
    private String branch; // 지점
    private Category category;
    private String menu; // 추천메뉴
    private String content; // 소개
    private String address; // 주소
    private Double lat; // 위도
    private Double lng; // 경도
    private int totalLikeCount; // 좋아요 갯수
    private Double starAvg; // 별점 평균

    public StoreDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.branch = store.getBranch();
        this.category = store.getCategory();
        this.menu = store.getMenu();
        this.content = store.getContent();
        this.address = store.getAddress();
        this.lat = store.getLat();
        this.lng = store.getLng();
        this.totalLikeCount = store.getTotalLikeCount();
        this.starAvg = store.getStarRating().getStarAvg();
    }
}
