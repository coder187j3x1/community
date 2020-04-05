package com.seaxll.community.cotroller;

import com.seaxll.community.dto.AccessTokenDTO;
import com.seaxll.community.dto.GithubUser;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import com.seaxll.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * ClassName: AuthorizeController
 * Description:
 * date: 2020/4/1 23:01
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state", required = false, defaultValue = "1") String state,
            HttpServletRequest request,
            HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            // 登录成功，
            String token = UUID.randomUUID().toString();

            User user = new User(githubUser.getName(), String.valueOf(githubUser.getId()), githubUser.getBio(), githubUser.getAvatarUrl());

            userMapper.insertUser(user);
            response.addCookie(new Cookie("token", token));

            return "redirect:/";
        } else {
            // 登录失败
            return "redirect:/";
        }
    }
}
