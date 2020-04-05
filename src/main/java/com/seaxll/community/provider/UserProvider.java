package com.seaxll.community.provider;

import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: UserProvider
 * Description:
 * date: 2020/4/5 19:05
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Service
public class UserProvider {
    @Autowired
    private UserMapper userMapper;

    /**
     * 验证 request 的 cookies 中是否存在用户的 token
     *  有就在数据库中查询用户，并将用户对象存入session
     *
     * @param request request
     * @return user 用户
     */
    public User userCookieVerify(HttpServletRequest request) {
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    // 通过 token 查询用户
                    user = userMapper.findUserByToken(token);
                    if (user != null) {
                        // 将用户信息存入 session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return user;
    }


}
