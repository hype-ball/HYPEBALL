package com.project.hypeball.dto;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter @Setter
public class ReviewSaveForm {

    @NotNull
    private Store store; // 리뷰가 딸린 가게

    @NotNull
    private Member member; // 작성자

    @NotNull
    private String content; // 리뷰내용

    @NotNull
    private String createdDate; // 작성일

    @NotNull
    private Double star; // 별점
}
