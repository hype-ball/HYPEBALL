package com.project.hypeball.controller;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Role;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import com.project.hypeball.service.StoreService;
import com.project.hypeball.web.ScriptUtil;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("store")
public class StoreController {

  private final StoreService storeService;

  @GetMapping("/home")
  public String list(Model model,
                     @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                     HttpServletResponse response) throws IOException {

    if (loginMember == null || loginMember.getRole() != Role.ROLE_ADMIN) {
      ScriptUtil.alertAndBackPage(response, "관리자만 접근 가능한 페이지입니다.");
      return null;
    }

    model.addAttribute("list", storeService.findAll());
    return "/store/storeList";
  }

  @GetMapping("/form")
  public String form(Model model,
                     @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                     HttpServletResponse response) throws IOException {
    if (loginMember == null || loginMember.getRole() != Role.ROLE_ADMIN) {
      ScriptUtil.alertAndBackPage(response, "관리자만 접근 가능한 페이지입니다.");
      return null;
    }

    model.addAttribute("form", new StoreSaveForm());
    return "/store/storeForm";
  }

  @PostMapping("/save")
  public String save(@Validated @ModelAttribute("form") StoreSaveForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/store/storeForm";
    }
    storeService.add(form);
    return "redirect:/store/home";
  }

  @GetMapping("/{storeId}")
  public String detail(@PathVariable("storeId") Long id, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                       HttpServletResponse response) throws IOException {
    if (loginMember == null || loginMember.getRole() != Role.ROLE_ADMIN) {
      ScriptUtil.alertAndBackPage(response, "관리자만 접근 가능한 페이지입니다.");
      return null;
    }

    model.addAttribute("store", storeService.getFetch(id));
    return "/store/storeDetail";
  }

  @GetMapping("/update/{storeId}")
  public String update(@PathVariable("storeId") Long id, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                       HttpServletResponse response) throws IOException {
    if (loginMember == null || loginMember.getRole() != Role.ROLE_ADMIN) {
      ScriptUtil.alertAndBackPage(response, "관리자만 접근 가능한 페이지입니다.");
      return null;
    }

    Store store = storeService.get(id);
    StoreUpdateForm form = new StoreUpdateForm();
    form.setId(store.getId());
    form.setName(store.getName());
    form.setBranch(store.getBranch());
    form.setCategory(store.getCategory());
    form.setMenu(store.getMenu());
    form.setContent(store.getContent());
    form.setAddress(store.getAddress());
    form.setLat(store.getLat());
    form.setLng(store.getLng());

    model.addAttribute("form", form);
    return "/store/storeUpdate";
  }

  @PostMapping("/update")
  public String update(@Validated @ModelAttribute("form") StoreUpdateForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/store/storeForm";
    }
    storeService.update(form);
    return "redirect:/store/home";
  }

  @PostMapping("/delete")
  public String delete(Long id) {
    storeService.delete(id);
    return "redirect:/store/home";
  }
}
