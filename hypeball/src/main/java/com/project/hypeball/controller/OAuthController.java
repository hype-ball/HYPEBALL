package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("oauth2")
public class OAuthController {

    @GetMapping("/loginInfo")
    public String oauthLonginInfo(Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oauthUser.getAttributes();
        return attributes.toString();
    }

    @GetMapping("/sessionInfo")
    public String oauthSessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionUser sessionUser = (SessionUser) session.getAttribute("member");
            System.out.println("request = " + session.getAttributeNames());
            return sessionUser.toString();
        }
        return "session == null";
    }

    @GetMapping("/session/clear")
    public String sessionClear(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "clear";
    }
}
