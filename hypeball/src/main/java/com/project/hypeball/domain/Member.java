package com.project.hypeball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id //PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column
    private String name;


}
