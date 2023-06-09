package com.project.hypeball.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
//        SessionUser member = (SessionUser) request.getSession(false).getAttribute("member");
        return "index";
    }
}