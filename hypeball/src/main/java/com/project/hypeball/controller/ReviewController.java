package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.*;

import com.project.hypeball.dto.*;
import com.project.hypeball.service.*;
import com.project.hypeball.web.FileStore;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final FileStore fileStore;
    private final StoreLikeService storeLikeService;

    /**
     * 가게 상세 정보 모달
     */
    @ResponseBody
    @GetMapping("/{id}")
    public Map<String, Object> allInfoOfStore (@PathVariable("id") Long storeId,
                                       @RequestParam("sort") String sort,
                                       @PageableDefault(size = 3) Pageable pageable,
                                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        HashMap<String, Object> map = new HashMap<>();

        if (loginMember != null) {
            if (storeLikeService.get(storeId, loginMember.getId()) == null) {
                map.put("status", "hate");
            } else {
                map.put("status", "like");
            }
        }

        StoreDto storeDto = new StoreDto(storeService.getFetch(storeId));
        map.put("store", storeDto);

        Page<ReviewDto> reviewDtos = reviewService.reviewsPaging(storeId, sort, pageable);
        map.put("reviews", reviewDtos);

        List<PointCountDto> points = reviewService.pointTagsRank(storeId);
        List<DrinkCountDto> drinks = reviewService.drinkTagsRank(storeId);

        map.put("points", points);
        map.put("drinks", drinks);

        return map;
    }

    /**
     * 리뷰 페이징 in 모달
     */
    @ResponseBody
    @GetMapping("/ps/{id}")
    public Page<ReviewDto> reviewsPart(@PathVariable("id") Long storeId,
                                       @RequestParam("sort") String sort,
                                       @PageableDefault(size = 3) Pageable pageable) {

        Page<ReviewDto> reviewDtos = reviewService.reviewsPaging(storeId, sort, pageable);

        return reviewDtos;
    }

    /**
     * 리뷰 저장
     */
    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> save(@Validated @RequestPart(value = "review") ReviewAddDto reviewAddDto, BindingResult bindingResult,
                                    @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                                    HttpServletResponse response) throws IOException {

        if (loginMember == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 정보가 필요합니다.");
            log.error("미인증 사용자 요청 : {} Error", response.getStatus());
            return null;
        }

        Store store = storeService.get(reviewAddDto.getStoreId());
        Member member = memberService.get(loginMember);

        log.info("reviewAdd = {}", reviewAddDto);
        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                log.info("multipartFile.getContentType = {}", multipartFile.getContentType());
                log.info("multipartFile.getOriginalFilename = {}", multipartFile.getOriginalFilename());
                log.info("multipartFile.getSize = {}", multipartFile.getSize());
            }
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

        List<AttachedFile> attachedFiles = null;
        if (multipartFiles != null) {
            // /static/files 에 저장
            attachedFiles = fileStore.storeFiles(multipartFiles);
        }

        reviewService.save(reviewAddDto, store, member, attachedFiles);

        map.put("result", "success");
        return map;
    }


    /**
     * 내가 쓴 리뷰 정렬 조회
     */
    @ResponseBody
    @GetMapping("/writer/")
    public List<MyReviewDto> reviewsByWriter(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                                             @RequestParam("sort") String sort) {

        return reviewService.reviewsByMember(loginMember.getId(), sort);
    }

    /**
     * 리뷰 삭제
     */
    @ResponseBody
    @DeleteMapping("/delete/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {

        log.info("리뷰 삭제 id = {}", reviewId);
        reviewService.deleteReview(reviewId);
    }
}
