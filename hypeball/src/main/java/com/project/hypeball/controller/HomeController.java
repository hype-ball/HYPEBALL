package com.project.hypeball.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @PostMapping("/search")
    public String search(@ModelAttribute("search") String keyword) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(keyword, "utf-8");
        return "redirect:/map/home?search=" + encode;
    }
}