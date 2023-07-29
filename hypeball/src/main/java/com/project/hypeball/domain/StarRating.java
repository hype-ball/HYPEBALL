package com.project.hypeball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StarRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "star_rating_id")
    private Long id;

    @Column
    @ColumnDefault("0.0")
    private Double totalValue; // 별점 총점

    @Column
    @ColumnDefault("0")
    private int totalCount; // 리뷰 갯수

    @Column
    @ColumnDefault("0.0")
    private Double starAvg; // 별점 평균

    public static StarRating createStarRating() {
        StarRating starRating = new StarRating();
        starRating.setTotalValue(0.0);
        starRating.setTotalCount(0);
        starRating.setStarAvg(0.0);
        return starRating;
    }

    public static StarRating updateStarRating(StarRating starRating, Double starByReview) {
        starRating.setTotalValue(starRating.getTotalValue() + starByReview);
        starRating.setTotalCount(starRating.getTotalCount() + 1);
        starRating.setStarAvg(starRating.getTotalValue() / starRating.getTotalCount());
        return starRating;
    }

    public static StarRating reduceStarRating(StarRating starRating, Double starByReview) {
        starRating.setTotalValue(starRating.getTotalValue() - starByReview);
        starRating.setTotalCount(starRating.getTotalCount() - 1);
        starRating.setStarAvg(starRating.getTotalValue() / starRating.getTotalCount());
        return starRating;
    }
}
