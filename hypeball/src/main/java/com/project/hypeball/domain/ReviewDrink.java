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
public class ReviewDrink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column
    @NotNull
    private String drink;

    public static ReviewDrink createReviewDrink(Review review, String drink) {
        ReviewDrink reviewDrink = new ReviewDrink();

        reviewDrink.setReview(review);
        reviewDrink.setDrink(drink);

        return reviewDrink;
    }
}
