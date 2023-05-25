package com.project.hypeball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Store implements Serializable {
    // 가게 설명 혹은 종류 필드 추가

    @Id //PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가
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
    private String menu; // 추천메뉴

    @Column
    private String content; // 소개

    @NotNull
    @Column
    private String address; // 주소

    @NotNull
    @Column
    private Double lat; // 위도

    @NotNull
    @Column
    private Double lng; // 경도

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    public static void saveReviewToStore(Store store, Review review) {
        store.getReviews().add(review);
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
}
