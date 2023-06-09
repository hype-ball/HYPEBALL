package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.StoreLikeService;
import com.project.hypeball.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final PointService pointService;
    private final StoreLikeService storeLikeService;
    private final MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) {

        if (loginMember == null) {
            log.error("로그인 정보가 존재하지 않습니다.");
            return "redirect:/"; // 로그인 모달로 수정,,
        }
        Member member = memberService.get(loginMember);

        model.addAttribute("likeList", storeLikeService.findByMember(member));
        return "myPage";
    }

    // map.html 중복,,,이게 맞나..?ㅎ
    @GetMapping("/myLike")
    public String map(Model model) {

        model.addAttribute("pointList", pointService.findAll());
        return "myLikeMap";
    }

    @ResponseBody
    @PostMapping("myLike")
    public Object myLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) {

        if (loginMember == null) {
            log.error("로그인 정보가 존재하지 않습니다.");
            return "redirect:/"; // 로그인 모달로 수정,,
        }
        Member member = memberService.get(loginMember);

        List<MarkerDto> markerByMember = storeLikeService.findMarkerByMember(member);
        return markerByMember;
    }
}
