package com.project.hypeball.dto;

import lombok.Data;

@Data
public class FieldErrorDto {

    String field;
    String message;

    public FieldErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
