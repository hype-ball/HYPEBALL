package com.project.hypeball.controller;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Point;
import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/{storeId}")
    public void reviews() {

    }

    // 멤버 수정해야함
    @ResponseBody
    @PostMapping("/add/{storeId}")
    public String save(@PathVariable("storeId") Long storeId,
                       @RequestParam Map<String, Object> param,
                       @RequestParam(value = "point[]") List<String> pointList,
                       @RequestParam(value = "drink[]") List<String> drinkList,
                       HttpServletRequest request) {
        Store store = storeService.get(storeId);
        Member member = memberService.get(1L);
        // 리뷰 저장
        Review review = reviewService.save(param, store, member);

        // 분위기태그 저장
        for (String pointId : pointList) {
            Point point = pointService.get(Long.valueOf(pointId));
            reviewService.reviewPointSave(review, point);
        }

        // 술태그 저장
        for (String drink : drinkList) {
            reviewService.reviewDrinkSave(review, drink);
        }

        return "success";
    }


}
