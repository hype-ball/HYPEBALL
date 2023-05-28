package com.project.hypeball.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.hypeball.dto.QAttachedFileQueryDto is a Querydsl Projection type for AttachedFileQueryDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAttachedFileQueryDto extends ConstructorExpression<AttachedFileQueryDto> {

    private static final long serialVersionUID = -934202259L;

    public QAttachedFileQueryDto(com.querydsl.core.types.Expression<Long> reviewId, com.querydsl.core.types.Expression<String> storeFileName) {
        super(AttachedFileQueryDto.class, new Class<?>[]{long.class, String.class}, reviewId, storeFileName);
    }

}

