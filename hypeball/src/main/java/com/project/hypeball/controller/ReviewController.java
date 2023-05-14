package com.project.hypeball.controller;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.ReviewSaveForm;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreService;
import com.sun.source.tree.MemberSelectTree;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreService storeService;

    @ResponseBody
    @GetMapping("/{storeId}")
    public List<Review> reviews(@PathVariable Long storeId) {

        Store store = storeService.get(storeId);
        System.out.println(storeId);
        List<Review> reviews = reviewService.getReviewsByStore(store);

        return reviews;
    }

}
