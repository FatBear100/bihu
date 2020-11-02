package com.ted.bihu.controller;

import com.ted.bihu.dto.PageDTO;
import com.ted.bihu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "pageIndex", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "3") Integer size
    ) {
        PageDTO pageDTO = questionService.list(page, size);
        model.addAttribute("page", pageDTO);
        return "index";
    }
}
