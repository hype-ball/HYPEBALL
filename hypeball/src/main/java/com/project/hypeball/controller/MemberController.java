package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.dto.FieldErrorDto;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.dto.MemberUpdateDto;
import com.project.hypeball.repository.MemberRepository;
import com.project.hypeball.service.MemberService;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.ReviewService;
import com.project.hypeball.service.StoreLikeService;
import com.project.hypeball.web.FileStore;
import com.project.hypeball.web.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.net.URI;
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
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) {

        if (loginMember == null) {
            log.error("미인증 사용자 요청 : redirect loginModal");
            return "redirect:/#loginModal";
        }

        Member member = memberService.get(loginMember);

        model.addAttribute("member", member);
        model.addAttribute("likeList", storeLikeService.findByMember(member));
        model.addAttribute("myReviews", reviewService.reviewsByMember(member.getId(), "default"));

        return "myPage";
    }

    @GetMapping("/myLike")
    public String map(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                      Model model) {

        if (loginMember == null) {
            log.error("미인증 사용자 요청 : redirect loginModal");
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

    @ResponseBody
    @PostMapping("/updateProfile")
    public Map<String, Object> updateProfile(@Validated @RequestPart(value = "nickname", required = false) MemberUpdateDto memberUpdateDto, BindingResult bindingResult,
                              @RequestPart(value = "picture", required = false) MultipartFile multipartFile,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER) LoginMember loginMember) throws IOException {

        System.out.println(memberUpdateDto.getName());
        System.out.println("picture = " + multipartFile);

        if (multipartFile != null) {
            System.out.println("첨부파일 있다꾸요");
        } else {
            System.out.println("첨부파일 없다용");
        }

        String picturePath = null;

        Map<String, Object> map = new HashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            List<FieldErrorDto> validations = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.info("fieldError = {},{},{},{}", fieldError.getField(), fieldError.getCode(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
                validations.add(new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            map.put("result", "valid");
            map.put("valid", validations);
            return map;
        }

        System.out.println("======ㅇㅔ러가 어디서 날까");

        Member findMember = memberService.get(loginMember);
        findMember.update(memberUpdateDto.getName());

        if (multipartFile != null) {
            picturePath = fileStore.storePicture(multipartFile);
            System.out.println("picturePath = " + picturePath);
            int profiles = picturePath.indexOf("profiles");
            System.out.println("profiles = " + profiles);
            String substring = picturePath.substring(profiles - 1);
            System.out.println("substring = " + substring);
            findMember.setPicture(substring);
        } else {
            System.out.println("사진 교체 안해용");
        }

        memberService.save(findMember);
        System.out.println("에러 발생지점 =====================");
        map.put("result", "success");
        System.out.println("맵에 넣었음");
        return map;
    }
}
