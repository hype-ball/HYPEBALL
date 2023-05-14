package com.project.hypeball.controller;

import com.project.hypeball.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @ResponseBody
    @PostMapping("/{storeId}")
    public void reviews() {

    }

}
