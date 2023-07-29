package com.project.hypeball.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberUpdateDto {

    @Size(min = 2, max = 10, message = "* 2~10자 이내로 작성해주세요.")
    private String name;
}
