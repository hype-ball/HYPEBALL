package com.project.hypeball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotNull
    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private Role role;

    private String picture;

    private String provider; // naver, kakao, google

    public Member(String name) {
        this.name = name;
    }

    @Builder
    public Member(String name, String email, Role role, String picture, String provider) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.picture = picture;
        this.provider = provider;
    }

    // 회원 업데이트
    public Member update(String name) {
        this.name = name;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
