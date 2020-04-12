package com.seaxll.community.interceptor;

import com.seaxll.community.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: SessionInterceptor
 * Description:
 * date: 2020/4/5 22:53
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserProvider userProvider;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        userProvider.userCookieVerify(request);
        //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", redirectUri);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
