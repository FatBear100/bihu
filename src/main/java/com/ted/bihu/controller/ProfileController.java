package com.ted.bihu.controller;

import com.ted.bihu.dto.PageDTO;
import com.ted.bihu.mapper.UserMapper;
import com.ted.bihu.model.User;
import com.ted.bihu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(
            @RequestParam(name = "pageIndex", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "3") Integer size,
            @PathVariable(name = "action") String action,
            HttpServletRequest request,
            Model model){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (action.equals("questions")){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        }else if (action.equals("replies")){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
        }
        if (user == null){
            return "redirect:/";
        }
        PageDTO pageDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("page", pageDTO);
        return "profile";
    }
}
