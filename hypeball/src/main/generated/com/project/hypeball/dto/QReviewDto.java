package com.project.hypeball.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.hypeball.dto.QReviewDto is a Querydsl Projection type for ReviewDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewDto extends ConstructorExpression<ReviewDto> {

    private static final long serialVersionUID = 1732054545L;

    public QReviewDto(com.querydsl.core.types.Expression<Long> reviewId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> createdDate, com.querydsl.core.types.Expression<Double> star, com.querydsl.core.types.Expression<String> writer) {
        super(ReviewDto.class, new Class<?>[]{long.class, String.class, String.class, double.class, String.class}, reviewId, content, createdDate, star, writer);
    }

}

