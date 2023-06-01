package com.project.hypeball.repository;

import com.project.hypeball.dto.DrinkCountDto;

import java.util.List;

public interface ReviewDrinkRepositoryInterface {
    List<DrinkCountDto> drinkCount(Long storeId);

}
