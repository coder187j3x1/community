package com.seaxll.community.provider;

import com.alibaba.fastjson.JSON;
import com.seaxll.community.dto.AccessTokenDTO;
import com.seaxll.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName: GithubProvider
 * Description:
 * date: 2020/4/2 20:26
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Component
public class GithubProvider {
    /**
     * 使用 accessTokenDTO 调用 OkHttp 接口 访问 https://github.com/login/oauth/access_token 并解析返回结果中的 token
     *
     * @param accessTokenDTO accessTokenDTO
     * @return String token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String json = JSON.toJSONString(accessTokenDTO);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用获取的 token 访问 https://api.github.com/user ,  并返回 GitHub 的 用户对象的 Json 字符串
     *
     * @param accessToken 访问 https://api.github.com/user 的 token
     * @return GithubUser
     */
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
