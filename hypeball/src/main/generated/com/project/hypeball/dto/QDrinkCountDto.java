package com.project.hypeball.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.hypeball.dto.QDrinkCountDto is a Querydsl Projection type for DrinkCountDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDrinkCountDto extends ConstructorExpression<DrinkCountDto> {

    private static final long serialVersionUID = 436782258L;

    public QDrinkCountDto(com.querydsl.core.types.Expression<String> drinkName, com.querydsl.core.types.Expression<Long> count) {
        super(DrinkCountDto.class, new Class<?>[]{String.class, long.class}, drinkName, count);
    }

}

