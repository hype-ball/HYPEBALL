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
    private String name;
    private String branch;
    private Category category;
    private String menu;
    private String content;
    private String address;
    private Double lat;
    private Double lng;
    private int totalLikeCount;
    private Double starAvg;

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
