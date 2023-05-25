package com.project.hypeball.controller;

import com.project.hypeball.domain.*;

import com.project.hypeball.dto.DrinkCountDto;
import com.project.hypeball.dto.PointCountDto;
import com.project.hypeball.dto.ReviewDto;
import com.project.hypeball.dto.ReviewSearchCondition;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final PointService pointService;

    @ResponseBody
    @GetMapping("/{storeId}")
    public Map<String, Object> reviews(@PathVariable Long storeId, Model model) {


        HashMap<String, Object> map = new HashMap<>();

        Store store = storeService.get(storeId);

        List<Review> reviews = store.getReviews();

        List<ReviewDto> review_list = new ArrayList<>();

        System.out.println("====================================");

        for (Review review : reviews) {
            System.out.println("review.getContent() = " + review.getContent());
            System.out.println("review.getStar() = " + review.getStar());
            review_list.add(new ReviewDto(review.getContent(), review.getCreatedDate(), review.getStar(), review.getMember().getName()));
        }

        System.out.println("====================================");

        map.put("reviews", review_list);

        List<PointCountDto> points = reviewService.pointTagsRank(storeId);
        List<DrinkCountDto> drinks = reviewService.drinkTagsRank(storeId);

        map.put("points", points);
        map.put("drinks", drinks);

        return map;
    }

    // 멤버 수정해야함
    @ResponseBody
    @PostMapping("/add/{storeId}")
    public Long save(@PathVariable("storeId") Long storeId,
                       @RequestPart(value = "review") Map<String, Object> param,
                       @RequestPart(value = "point") List<Long> pointList,
                       @RequestPart(value = "drink") List<String> drinkList,
                       @RequestPart(value = "file", required = false) MultipartFile[] multipartFiles,
                       HttpServletRequest request) {
        Store store = storeService.get(storeId);
        Member member = memberService.get(1L);
        System.out.println("=======");
        System.out.println("param = " + param);
        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
            }
        }
        if (pointList != null) {
            for (Long pointId : pointList) {
                System.out.println("pointId = " + pointId);
            }
        }
        if (drinkList != null) {
            for (String drink : drinkList) {
                System.out.println("drink = " + drink);
            }
        }

        // 리뷰 저장
        Review review = reviewService.save(param, store, member, pointList, drinkList);

        return store.getId();
    }

    /**
     * drink count
     */
    @ResponseBody
    @GetMapping("/test/drinkTags/{storeId}")
    public List<DrinkCountDto> drinkTest(@PathVariable Long storeId) {
        List<DrinkCountDto> drinkCountDtos = reviewService.drinkTagsRank(storeId);

        for (DrinkCountDto drinkCountDto : drinkCountDtos) {
            System.out.println("====================================");
            System.out.println("drinkCountDto.getDrinkName() = " + drinkCountDto.getDrinkName());
            System.out.println("drinkCountDto.getCount() = " + drinkCountDto.getCount());
        }

        return drinkCountDtos;
    }

    /**
     * point count;
     */

    @ResponseBody
    @GetMapping("/test/pointTags/{storeId}")
    public List<PointCountDto> pointTest(@PathVariable Long storeId) {
        List<PointCountDto> pointCountDtos = reviewService.pointTagsRank(storeId);

        for (PointCountDto pointCountDto : pointCountDtos) {
            System.out.println("====================================");
            System.out.println("pointCountDto.getPointName() = " + pointCountDto.getPointName());
            System.out.println("pointCountDto.getCount() = " + pointCountDto.getCount());
        }

        return pointCountDtos;
    }

    @ResponseBody
    @GetMapping("/test/reviews")
    public Page<ReviewDto> searchMemberV3(ReviewSearchCondition condition, Pageable pageable) {
        return reviewService.reviewsPaging(condition, pageable);
    }

}
