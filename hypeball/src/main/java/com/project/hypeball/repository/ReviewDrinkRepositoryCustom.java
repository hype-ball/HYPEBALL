package com.project.hypeball.repository;

import com.project.hypeball.dto.DrinkCountDto;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ReviewDrinkRepositoryCustom {

    List<DrinkCountDto> drinkCount(Long storeId);

}
