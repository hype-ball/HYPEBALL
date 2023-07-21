package com.project.hypeball.controller;

import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerCardDto;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final StoreService storeService;
    private final PointService pointService;

    @GetMapping("/home")
    public String map(Model model) {

        model.addAttribute("pointList", pointService.findAll());
        return "map";
    }

    /**
     * 1. 모든 가게 정보
     * @return MakerDto
     *
     * 2. 지도에서 검색 (가게이름 , 주소)
     * @return MakerCardDto
     */
    @ResponseBody
    @PostMapping("/home")
    public Map<String, Object> marker(@RequestParam("keyword") String keyword) {

        Map<String, Object> map = new HashMap<>();

        if (!keyword.isEmpty()) {
            map.put("search", storeService.search(keyword));
            log.info("keyword = {}", keyword);
        }

        List<Store> list = storeService.findAll();
        List<MarkerDto> storeList = new ArrayList<>();
        for (Store store : list) {
            storeList.add(new MarkerDto(store.getId(), store.getName(), store.getLat(), store.getLng()));
        }
        map.put("marker", storeList);
        map.put("keyword", keyword);
        return map;
    }

    @GetMapping("/rank/{keyword}")
    public String mapOfRanks(@PathVariable("keyword") String keyword, Model model) {

        model.addAttribute("pointList", pointService.findAll());
        return "map";
    }

    /**
     * 통계로 보는 하이볼 상위 10개
     * @return MarkerCardDto
     */
    @ResponseBody
    @PostMapping("/rank/{keyword}")
    public List<MarkerCardDto> ranksByStar(@PathVariable("keyword") String keyword) {

        log.info("statistic={}", keyword);
        if (keyword.equals("star")) return storeService.findRanksByStar(10); // 별점 높은 순
        if (keyword.equals("like")) return storeService.findRanksByLike(10); // 찜 많은 순
        if (keyword.equals("review")) return storeService.findRanksByReview(10); // 리뷰 많은 순
        if (keyword.equals("new")) return storeService.findRanksByNew(10); // 신규 등록순

        return new ArrayList<>();
    }

    /**
     * 지역별 하이볼 명가
     * @param region
     * @return MarkerDto
     */
    @ResponseBody
    @PostMapping("/home/{region}")
    public List<MarkerDto> markerByRegion(@PathVariable("region") String region) {

        List<Store> list;
        String regionKr = null;

        if (region.equals("gangnam")) {
            regionKr = "강남";
        } else if (region.equals("yongsan")) {
            regionKr = "용산";
        } else if (region.equals("jamsil")) {
            regionKr = "송파";
        } else if (region.equals("hongdae")) {
            regionKr = "마포";
        }

        list = storeService.findByRegion(regionKr);
        log.info("region={}", region);
        List<MarkerDto> storeList = new ArrayList<>();
        for (Store store : list) {
            storeList.add(new MarkerDto(store.getId(), store.getName(), store.getLat(), store.getLng()));
        }
        return storeList;
    }

}