package com.project.hypeball.repository;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;

import java.util.List;

public interface ReviewRepositoryInterface {

    public List<Review> findReviewsFetch(Store store);
}
