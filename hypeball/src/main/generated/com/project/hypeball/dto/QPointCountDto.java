package com.project.hypeball.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.hypeball.dto.QPointCountDto is a Querydsl Projection type for PointCountDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPointCountDto extends ConstructorExpression<PointCountDto> {

    private static final long serialVersionUID = 1736444330L;

    public QPointCountDto(com.querydsl.core.types.Expression<String> pointName, com.querydsl.core.types.Expression<Long> count) {
        super(PointCountDto.class, new Class<?>[]{String.class, long.class}, pointName, count);
    }

}

