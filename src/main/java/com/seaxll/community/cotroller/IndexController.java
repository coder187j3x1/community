package com.seaxll.community.cotroller;

import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import com.seaxll.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: IndexController
 * Description:
 * date: 2020/3/31 23:05
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        // Todo 加载问题列表
        List<QuestionDTO> questionList = questionService.getQuestionList(page, size);
        model.addAttribute("questions", questionList);
        return "index";
    }
}
