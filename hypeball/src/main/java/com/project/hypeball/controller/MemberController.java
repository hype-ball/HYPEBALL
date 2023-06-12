package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreLikeService;
import com.project.hypeball.web.ScriptUtil;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final PointService pointService;
    private final StoreLikeService storeLikeService;
    private final MemberService memberService;
    private final ReviewService reviewService;

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) throws IOException {

        if (loginMember == null) {
            log.error("로그인 정보가 존재하지 않습니다.");
            return "redirect:/#loginModal";
        }
        Member member = memberService.get(loginMember);

        model.addAttribute("likeList", storeLikeService.findByMember(member));
        model.addAttribute("myReviews", reviewService.reviewsByMember(member.getId(), "default"));

        return "myPage";
    }

    @GetMapping("/myLike")
    public String map(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                      Model model) throws IOException {

        if (loginMember == null) {
            log.error("로그인 정보가 존재하지 않습니다.");
            return "redirect:/#loginModal";
        }

        model.addAttribute("pointList", pointService.findAll());
        return "map";
    }

    @ResponseBody
    @PostMapping("myLike")
    public List<MarkerDto> myLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        Member member = memberService.get(loginMember);

        List<MarkerDto> markerByMember = storeLikeService.findMarkerByMember(member);
        return markerByMember;
    }
}
