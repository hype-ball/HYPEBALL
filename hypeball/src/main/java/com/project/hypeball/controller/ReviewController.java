package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.*;

import com.project.hypeball.dto.*;
import com.project.hypeball.service.*;
import com.project.hypeball.web.FileStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @ResponseBody
    @GetMapping("/{id}")
    public Map<String, Object> reviews (@PathVariable("id") Long storeId,
                                       @RequestParam("sort") String sort,
                                       @PageableDefault(size = 3) Pageable pageable) {

        HashMap<String, Object> map = new HashMap<>();

        Store store = storeService.getFetch(storeId);

        Page<ReviewDto> reviewDtos = reviewService.reviewsPaging(storeId, sort, pageable);

        map.put("store", store);
        map.put("reviews", reviewDtos);

        List<PointCountDto> points = reviewService.pointTagsRank(storeId);
        List<DrinkCountDto> drinks = reviewService.drinkTagsRank(storeId);

        map.put("points", points);
        map.put("drinks", drinks);

        return map;
    }

    @ResponseBody
    @GetMapping("/ps/{id}")
    public Page<ReviewDto> reviewsPart(@PathVariable("id") Long storeId,
                                       @RequestParam("sort") String sort,
                                       @PageableDefault(size = 3) Pageable pageable) {

        Page<ReviewDto> reviewDtos = reviewService.reviewsPaging(storeId, sort, pageable);
        System.out.println("reviewDtos = " + reviewDtos);

        return reviewDtos;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> save(@Validated @RequestPart(value = "review") ReviewAddDto reviewAddDto, BindingResult bindingResult,
                                    @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                    @SessionAttribute LoginMember loginMember) throws IOException {

        // 멤버 없을 때 에러처리

        Store store = storeService.get(reviewAddDto.getStoreId());
        Member member = memberService.get(loginMember);

        if (multipartFiles != null) {
            log.info("================== file upload start ======================");
            for (MultipartFile multipartFile : multipartFiles) {
                log.info("multipartFile.getContentType = " + multipartFile.getContentType());
                log.info("multipartFile.getOriginalFilename = " + multipartFile.getOriginalFilename());
                log.info("multipartFile.getSize = " + multipartFile.getSize());
            }
            log.info("================== file upload end ======================");
        }
        log.info("================== review ======================");
        log.info("reviewAdd = " + reviewAddDto);

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

        //  리뷰 저장
        Review review = reviewService.save(reviewAddDto, store, member, attachedFiles);

        map.put("result", "success");
        return map;
    }


    /**
     * 작성자로 리뷰찾기
     */
    @ResponseBody
    @GetMapping("/test/{memberId}")
    public List<MyReviewDto> reviewsByWriter(@PathVariable("memberId") Long memberId, @RequestParam("sort") String sort) {

        System.out.println("ReviewController.reviewsByWriter");
        System.out.println(sort);

        return reviewService.reviewsByMember(memberId, sort);
    }

    @ResponseBody
    @DeleteMapping("/delete/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {

        System.out.println("ReviewController.deleteReview");


        reviewService.deleteReview(reviewId);
    }


    /**
     * drink count
     */
    @ResponseBody
    @GetMapping("/test/drinkTags/{storeId}")
    public List<DrinkCountDto> drinkTest(@PathVariable Long storeId) {
        List<DrinkCountDto> drinkCountDtos = reviewService.drinkTagsRank(storeId);

        for (DrinkCountDto drinkCountDto : drinkCountDtos) {
            System.out.println("====================================");
            System.out.println("drinkCountDto.getDrinkName() = " + drinkCountDto.getDrinkName());
            System.out.println("drinkCountDto.getCount() = " + drinkCountDto.getCount());
        }

        return drinkCountDtos;
    }

    /**
     * point count;
     */
    @ResponseBody
    @GetMapping("/test/pointTags/{storeId}")
    public List<PointCountDto> pointTest(@PathVariable Long storeId) {
        List<PointCountDto> pointCountDtos = reviewService.pointTagsRank(storeId);

        for (PointCountDto pointCountDto : pointCountDtos) {
            System.out.println("====================================");
            System.out.println("pointCountDto.getPointName() = " + pointCountDto.getPointName());
            System.out.println("pointCountDto.getCount() = " + pointCountDto.getCount());
        }

        return pointCountDtos;
    }
}
