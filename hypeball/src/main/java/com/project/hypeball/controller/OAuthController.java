package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        if (request.getSession(false) != null) {
            SessionUser sessionUser = (SessionUser) request.getSession(false).getAttribute("member");
            return sessionUser.toString();
        }
        return "session == null";
    }
}
