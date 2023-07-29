package com.project.hypeball.config.auth.dto;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private String provider;
    private Role role;

    public LoginMember(Member member) {
        this.id = member.getId();
        this.name = member.getNickname();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.provider = member.getProvider();
        this.role = member.getRole();
    }

    @Override
    public String toString() {
        return "LoginMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", provider='" + provider + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
