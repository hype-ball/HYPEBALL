package com.project.hypeball.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ReviewAddDto {

    private Long storeId;

    @NotEmpty(message = "* 필수 입력 사항입니다.")
    private List<String> drinkList;

    @NotEmpty(message = "* 필수 선택 사항입니다.")
    private List<Long> pointList;

    private String star;

    @Size(min = 5, max = 100, message = "* 5~100자 이내로 작성해주세요.")
    private String content;
}
