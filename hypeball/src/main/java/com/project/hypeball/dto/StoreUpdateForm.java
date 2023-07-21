package com.project.hypeball.dto;

import com.project.hypeball.domain.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreUpdateForm {

  @NotNull
  private Long id;

  @NotNull
  @Size(min =1, max = 10, message = "상호명은 10자 이하입니다.")
  private String name;

  @Size(min =1, max = 10, message = "지점명은 10자 이하입니다.")
  private String branch;

  private Category category;

  private String menu;

  private String content;

  @NotNull
  private String address;

  @NotNull
  private Double lat;

  @NotNull
  private Double lng;
}
