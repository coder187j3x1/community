package com.seaxll.community.cotroller;

import com.seaxll.community.dto.PaginationDTO;
import com.seaxll.community.service.QuestionService;
import com.seaxll.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        userService.userCookieVerify(request);
        PaginationDTO pagination = questionService.getQuestionList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
