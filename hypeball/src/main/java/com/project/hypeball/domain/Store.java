package com.project.hypeball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate // 변경된 필드만 update
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @NotNull
    @Column(length = 10)
    private String name; // 상호명

    @Column(length = 10)
    private String branch; // 지점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String menu;

    @Column
    private String content;

    @NotNull
    @Column
    private String address;

    @NotNull
    @Column
    private Double lat;

    @NotNull
    @Column
    private Double lng;

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @ColumnDefault("0")
    private int totalLikeCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_rating_id")
    private StarRating starRating;

    public static void calcStarAvg(Store store, Review review) {
        store.setStarRating(StarRating.updateStarRating(store.getStarRating(), review.getStar()));
    }

    public static void reduceStarAvg(Store store, Review review) {
        store.setStarRating(StarRating.reduceStarRating(store.getStarRating(), review.getStar()));
    }

    public static Store createStore(StoreSaveForm form) {
        Store store = new Store();
        store.setName(form.getName());
        store.setBranch(form.getBranch());
        store.setCategory(form.getCategory());
        store.setMenu(form.getMenu());
        store.setContent(form.getContent());
        store.setAddress(form.getAddress());
        store.setLat(form.getLat());
        store.setLng(form.getLng());
        store.setTotalLikeCount(0);
        store.setStarRating(StarRating.createStarRating());
        return store;
    }

    public void updateStore(Store store, StoreUpdateForm form) {
        store.setId(form.getId());
        store.setName(form.getName());
        store.setBranch(form.getBranch());
        store.setCategory(form.getCategory());
        store.setMenu(form.getMenu());
        store.setContent(form.getContent());
        store.setAddress(form.getAddress());
        store.setLat(form.getLat());
        store.setLng(form.getLng());
    }

    public static int addCount(Store store) {
        store.totalLikeCount += 1;
        return store.getTotalLikeCount();
    }

    public static int removeCount(Store store) {
        int restCount = store.totalLikeCount - 1;
        if (restCount < 0) {
            throw new IllegalArgumentException("Invalid count: " + restCount);
        }
        store.totalLikeCount = restCount;
        return store.getTotalLikeCount();
    }
}
