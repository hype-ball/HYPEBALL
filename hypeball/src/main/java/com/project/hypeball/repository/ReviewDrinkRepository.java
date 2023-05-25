package com.project.hypeball.repository;

import com.project.hypeball.domain.ReviewDrink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDrinkRepository extends JpaRepository<ReviewDrink, Long>, ReviewDrinkRepositoryCustom {

}
