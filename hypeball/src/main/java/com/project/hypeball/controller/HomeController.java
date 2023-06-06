package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
//        SessionUser member = (SessionUser) request.getSession(false).getAttribute("member");
        return "index";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "myPage";
    }
}