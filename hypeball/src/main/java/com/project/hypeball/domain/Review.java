package com.project.hypeball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hypeball.dto.ReviewAddDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column
    private String content;

    @CreatedDate
    @NotNull
    @Column
    private String createdDate;

    @NotNull
    @Column
    private Double star;

    @JsonIgnore
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<AttachedFile> imageFiles;

    public static Review createReview(ReviewAddDto reviewAddDto, Store store, Member member) {
        Review review = new Review();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        review.setStore(store);
        review.setMember(member);
        review.setContent(reviewAddDto.getContent());
        review.setCreatedDate(LocalDateTime.now().format(dtf));
        review.setStar(Double.parseDouble(reviewAddDto.getStar()) / 2);
        return review;
    }
}
