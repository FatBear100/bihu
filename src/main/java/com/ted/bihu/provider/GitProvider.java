package com.ted.bihu.provider;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ted.bihu.dto.AccessTokenDTO;
import com.ted.bihu.dto.GitUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Component
public class GitProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, com.alibaba.fastjson.JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            System.out.println(result);
            result = result.split("&")[0].split("=")[1];
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitUser getUser(String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=" + token)
                .addHeader("Authorization", "token " + token)
                .url("https://api.github.com/user")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GitUser gitUser = com.alibaba.fastjson.JSON.parseObject(str, GitUser.class);
            return gitUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
