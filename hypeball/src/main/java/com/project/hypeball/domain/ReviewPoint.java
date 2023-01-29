package com.project.hypeball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReviewPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    public static ReviewPoint createReviewPoint(Review review, Point point) {
        ReviewPoint reviewPoint = new ReviewPoint();

        reviewPoint.setReview(review);
        reviewPoint.setPoint(point);

        return reviewPoint;
    }
}
