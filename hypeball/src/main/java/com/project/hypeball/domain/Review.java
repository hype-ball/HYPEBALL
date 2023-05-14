package com.project.hypeball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    // 리뷰글 내용 done
    // 작성자 done
    // 리뷰등록일 done
    // 별점
    // 가게 번호 done
    // 첨부 사진
    // 태그명
    // 지정태그 사용
    // 댓글 좋아요

    @Id //PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가
    @Column(name = "review_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store; // 리뷰가 딸린 가게

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자

    @NotNull
    @Column
    private String content; // 리뷰내용

    @CreatedDate
    @NotNull
    @Column
    private String createdDate; // 작성일

    @NotNull
    @Column
    private Double star; // 별점

    public static Review createReview(Map<String, Object> param, Store store, Member member) {
        Review review = new Review();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        review.setStore(store);
        review.setMember(member);
        review.setContent(param.get("content").toString());
        review.setCreatedDate(LocalDateTime.now().format(dtf));
        review.setStar(Double.parseDouble((String) param.get("star")) / 2);

        return review;
    }
}
