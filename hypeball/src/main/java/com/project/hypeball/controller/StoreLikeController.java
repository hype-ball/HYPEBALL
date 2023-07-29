package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Store;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.StoreLikeService;
import com.project.hypeball.service.StoreService;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("store")
public class StoreLikeController {

    private final MemberService memberService;
    private final StoreService storeService;
    private final StoreLikeService storeLikeService;

    /**
     * 찜
     */
    @ResponseBody
    @PostMapping("/like/{id}")
    public Map<String, Object> like(@PathVariable("id") Long storeId,
                                    @ModelAttribute("status") String status,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                                    HttpServletResponse response) throws IOException {

        if (loginMember == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 정보가 필요합니다.");
            log.error("미인증 사용자 요청 : {} Error", response.getStatus());
            return null;
        }

        Member member = memberService.get(loginMember);
        Store store = storeService.get(storeId);

        log.info("storeLike = status : {}, storeId : {}, member : {} ", status, storeId, loginMember.getName());
        Map<String, Object> map = new HashMap<>();
        int storeLikeCount;
        if (status.equals("like")) {
            storeLikeCount = storeLikeService.save(store, member);
            map.put("result", "success");
        } else if (status.equals("hate")) {
            storeLikeCount = storeLikeService.delete(store, member);
            map.put("result", "delete");
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        map.put("count", storeLikeCount);
        return map;
    }
}
