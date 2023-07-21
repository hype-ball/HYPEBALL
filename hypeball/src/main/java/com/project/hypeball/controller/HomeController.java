package com.project.hypeball.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * 홈화면
     * 가게 이름, 주소 검색
     */
    @PostMapping("/search")
    public String search(@ModelAttribute("search") String keyword) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(keyword, "utf-8");
        log.info("keyword = {}", keyword);
        return "redirect:/map/home?search=" + encode;
    }
}