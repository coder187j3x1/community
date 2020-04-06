package com.seaxll.community.cotroller;

import com.seaxll.community.dto.AccessTokenDTO;
import com.seaxll.community.dto.GithubUser;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import com.seaxll.community.provider.GithubProvider;
import com.seaxll.community.service.UserService;
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
    @Autowired
    private UserService userService;

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
            HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId, clientSecret, code, redirectUri, state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            // 登录成功
            String token = UUID.randomUUID().toString();
            User user = userService.verifyUser(githubUser, token);
            response.addCookie(new Cookie("token", user.getToken()));
            return "redirect:/";
        } else {
            // 登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
