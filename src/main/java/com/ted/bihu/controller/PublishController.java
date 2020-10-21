package com.ted.bihu.controller;

import com.ted.bihu.mapper.QuestionMapper;
import com.ted.bihu.mapper.UserMapper;
import com.ted.bihu.model.Question;
import com.ted.bihu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "desc", required = false) String desc,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title", title);
        model.addAttribute("desc", desc);
        model.addAttribute("tag", tag);
        if (title == null || title.isEmpty() || desc == null || desc.isEmpty()){
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        if (user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDesc(desc);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());

        questionMapper.create(question);
        return "redirect:/";
    }

}
