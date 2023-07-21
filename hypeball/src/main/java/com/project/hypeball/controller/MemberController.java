package com.project.hypeball.controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final PointService pointService;
    private final StoreLikeService storeLikeService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final FileStore fileStore;

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) {

        if (loginMember == null) {
            log.error("미인증 사용자 요청 : redirect loginModal");
            return "redirect:/#loginModal";
        }

        Member member = memberService.get(loginMember);

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

    /**
     * 찜한 가게 지도로 보기
     * @return MarkerDto
     */
    @ResponseBody
    @PostMapping("myLike")
    public List<MarkerDto> myLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        Member member = memberService.get(loginMember);

        List<MarkerDto> markerByMember = storeLikeService.findMarkerByMember(member);
        return markerByMember;
    }

    /**
     * 프로필 수정 (닉네임, 사진)
     */
    @ResponseBody
    @PostMapping("/updateProfile")
    public Map<String, Object> updateProfile(@Validated @RequestPart(value = "nickname", required = false) MemberUpdateDto memberUpdateDto, BindingResult bindingResult,
                                             @RequestPart(value = "picture", required = false) MultipartFile multipartFile,
                                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                                             HttpServletResponse response, HttpSession httpSession) throws IOException {

        if (loginMember == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 정보가 필요합니다.");
            log.error("미인증 사용자 요청 : {} Error", response.getStatus());
            return null;
        }

        log.info("memberUpdateDto = {} ", memberUpdateDto);

        if (multipartFile != null) {
            log.info("multipartFile = {}, {}, {} " + multipartFile.getContentType(), multipartFile.getOriginalFilename(), multipartFile.getSize());
        }

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

        Member member = memberService.get(loginMember);
        String filepath = null;

        if (multipartFile != null) {
            String pictureFullPath = fileStore.storePicture(multipartFile);
            log.info("pictureFullPath={}", pictureFullPath);
            int profiles = pictureFullPath.indexOf("profiles");
            filepath = pictureFullPath.substring(profiles - 1);
            log.info("filepath={}", filepath);
        }

        memberService.update(member, memberUpdateDto.getName(), filepath);
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(member));

        map.put("result", "success");
        return map;
    }
}


