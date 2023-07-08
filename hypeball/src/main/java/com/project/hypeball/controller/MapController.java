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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

  private final StoreService storeService;
  private final PointService pointService;

  @GetMapping("/home")
  public String map(Model model) {

    System.out.println("MapController.map");

    model.addAttribute("pointList", pointService.findAll());
    return "map";
  }

  @ResponseBody
  @PostMapping("/home")
  public Map<String, Object> marker(@RequestParam("keyword") String keyword) {

    System.out.println("MapController.marker");
    Map<String, Object> map = new HashMap<>();

    if (!keyword.isEmpty()) {
      map.put("search", storeService.search(keyword));
    }
    System.out.println("==============" + keyword);

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

//    if (!keyword.equals("star") && !keyword.equals("like") && !keyword.equals("review") && !keyword.equals("new")) return "error";

    model.addAttribute("pointList", pointService.findAll());
    return "map";
  }

  @ResponseBody
  @PostMapping("/rank/{keyword}")
  public List<MarkerCardDto> ranksByStar(@PathVariable("keyword") String keyword) {

    if (keyword.equals("star")) return storeService.findRanksByStar(10);
    if (keyword.equals("like")) return storeService.findRanksByLike(10);
    if (keyword.equals("review")) return storeService.findRanksByReview(10);
    if (keyword.equals("new")) return storeService.findRanksByNew(10);

    return null;
  }

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
    List<MarkerDto> storeList = new ArrayList<>();
    for (Store store : list) {
      storeList.add(new MarkerDto(store.getId(), store.getName(), store.getLat(), store.getLng()));
    }
    return storeList;
  }


//  @ResponseBody
//  @PostMapping("/rank/star")
//  public List<MarkerRankDto> ranksByStar() {
//
//    return storeService.findRanksByStar(10);
//  }
//
//  @ResponseBody
//  @PostMapping("/rank/like")
//  public List<MarkerRankDto> ranksByLike() {
//
//    return storeService.findRanksByLike(10);
//  }
//
//  @ResponseBody
//  @PostMapping("/rank/review")
//  public List<MarkerRankDto> ranksByReview() {
//
//    return storeService.findRanksByReview(10);
//  }
}