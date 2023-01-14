package com.project.hypeball.controller;

import com.project.hypeball.domain.Store;
import com.project.hypeball.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final StoreService storeService;

    @GetMapping("/home")
    public String map(Model model) {
        model.addAttribute("list", storeService.findAll());
        return "map";
    }

    @ResponseBody
    @PostMapping("/home")
    public List<Store> marker() {
        List<Store> list = storeService.findAll();
        return list;
    }
}

