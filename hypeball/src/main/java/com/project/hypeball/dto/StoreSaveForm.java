package com.project.hypeball.dto;

import com.project.hypeball.domain.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreSaveForm {

  @NotNull
  @Size(min =1, max = 10, message = "상호명은 10자 이하입니다.")
  private String name;

  @Size(min =1, max = 10, message = "지점명은 10자 이하입니다.")
  private String branch;

  private Category category;

  private String menu; // 추천메뉴

  private String content; // 소개

  @NotNull
  private String address; // 주소

  @NotNull
  private Double lat; // 위도

  @NotNull
  private Double lng; // 경도
}
