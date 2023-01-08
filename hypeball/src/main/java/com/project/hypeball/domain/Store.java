package com.project.hypeball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.io.Serializable;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
    private String category_id;

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
}
