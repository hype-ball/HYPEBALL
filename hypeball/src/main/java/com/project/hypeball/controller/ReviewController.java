package com.project.hypeball.controller;

import com.project.hypeball.domain.*;

import com.project.hypeball.dto.ReviewDto;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
//        List<Review> reviews = reviewService.getReviewsByStore(store);

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

        List<ReviewPoint> reviewPoints = reviewService.reviewPoints(storeId);
        String[] points = new String[reviewPoints.size()];

        int i = 0;

        for (ReviewPoint reviewPoint : reviewPoints) {
            points[i] = reviewPoint.getPoint().getName();
            i++;
        }

        map.put("points", points);

        return map;
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
        store.getReviews().add(review);


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
