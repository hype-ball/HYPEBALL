package com.project.hypeball.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.MarkerDto;
import com.project.hypeball.dto.MarkerRankDto;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.StoreService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
  public List<MarkerDto> marker() {

    System.out.println("MapController.marker");

    List<Store> list = storeService.findAll();
    List<MarkerDto> storeList = new ArrayList<>();
    for (Store store : list) {
      storeList.add(new MarkerDto(store.getId(), store.getName(), store.getLat(), store.getLng()));
    }
    return storeList;
  }

  @GetMapping("/rank/{keyword}")
  public String mapOfRanks(@PathVariable("keyword") String keyword, Model model) {

//    if (!keyword.equals("star") && !keyword.equals("like") && !keyword.equals("review")) return "error";

    model.addAttribute("pointList", pointService.findAll());
    return "map";
  }

  @ResponseBody
  @PostMapping("/rank/{keyword}")
  public List<MarkerRankDto> ranksByStar(@PathVariable("keyword") String keyword) {

    if (keyword.equals("star")) return storeService.findRanksByStar(10);
    if (keyword.equals("like")) return storeService.findRanksByLike(10);
    if (keyword.equals("review")) return storeService.findRanksByReview(10);

    return null;
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