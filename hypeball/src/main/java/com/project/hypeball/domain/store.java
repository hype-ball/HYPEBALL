package com.project.hypeball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class store {

    @Id //PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가
    @Column(name = "store_id")
    private Long id;

    @NotNull
    @Column(length = 30)
    private String title;

    @NotNull
    @Column
    private String address; // 주소

    @NotNull
    @Column
    private Long lat; // 위도

    @NotNull
    @Column
    private Long lng; // 경도
}
