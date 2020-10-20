package com.ted.bihu.controller;

import com.ted.bihu.dto.AccessTokenDTO;
import com.ted.bihu.dto.GitUser;
import com.ted.bihu.mapper.UserMapper;
import com.ted.bihu.model.User;
import com.ted.bihu.provider.GitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private GitProvider gitProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.uri}")
    private String clientUri;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callBack")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String token = gitProvider.getAccessToken(accessTokenDTO);
        GitUser gitUser = gitProvider.getUser(token);
        System.out.println(gitUser.getName());
        if (gitUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitUser.getName());
            user.setAccountId(String.valueOf(gitUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);

            request.getSession().setAttribute("user", gitUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
