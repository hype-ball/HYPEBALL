package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Store;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.StoreLikeService;
import com.project.hypeball.service.StoreService;
import com.project.hypeball.web.ScriptUtil;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping("/like/{id}")
    public Map<String, Object> like(@PathVariable("id") Long storeId,
                                    @ModelAttribute("status") String status,
                                    @SessionAttribute LoginMember loginMember) throws Exception {

        Member member = memberService.get(loginMember);
        Store store = storeService.get(storeId);

        log.info("storeLike = status : {}, storeId : {}, member : {} ", status, storeId, loginMember.getName());
        Map<String, Object> map = new HashMap<>();
        if (status.equals("like")) {
            storeLikeService.save(store, member);
            map.put("result", "success");
        } else if (status.equals("hate")) {
            storeLikeService.delete(store, member);
            map.put("result", "delete");
        } else {
            throw new IllegalArgumentException();
        }

        return map;
    }

    // form 방식
    @PostMapping("/del-like")
    public String myPage(Long storeId,
                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember) throws Exception {

        Member member = memberService.get(loginMember);
        Store store = storeService.get(storeId);

        storeLikeService.delete(store, member);
        return "redirect:/member/myPage";
    }
}
