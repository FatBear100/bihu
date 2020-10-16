package com.ted.bihu.controller;

import com.ted.bihu.dto.AccessTokenDTO;
import com.ted.bihu.dto.GitUser;
import com.ted.bihu.provider.GitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private GitProvider gitProvider;

    @GetMapping("/callBack")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:9980/callBack");
        accessTokenDTO.setClient_id("c83fa181e0b933b547bf");
        accessTokenDTO.setClient_secret("64c8f5f1c9b17396a212b63189a97dc773d92dba");
        String token = gitProvider.getAccessToken(accessTokenDTO);
        GitUser gitUser = gitProvider.getUser(token);
        System.out.println(gitUser.getName());
        return "index";
    }
}
