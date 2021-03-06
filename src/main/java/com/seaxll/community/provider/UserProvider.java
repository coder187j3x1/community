package com.seaxll.community.provider;

import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import com.seaxll.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @Autowired
    private NotificationService notificationService;

    /**
     * 验证 request 的 cookies 中是否存在用户的 token
     *  有就在数据库中查询用户，并将用户对象存入session
     *
     * @param request request
     */
    public void userCookieVerify(HttpServletRequest request) {
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
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        Integer unreadCount = notificationService.unreadCount(user.getId());
                        if (unreadCount > 0) {
                            session.setAttribute("unreadCount", unreadCount);
                        } else {
                            session.removeAttribute("unreadCount");
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     *
     * @param user user
     * @param other otherUser
     * @return
     */
    public boolean isUserEquals(User user, User other) {
        if (!user.getAvatarUrl().equals(other.getAvatarUrl())) {
            return false;
        }
        if (!user.getName().equals(other.getName())) {
            return false;
        }
        if (!user.getAccountId().equals(other.getAccountId())) {
            return false;
        }
        return true;
    }


}
