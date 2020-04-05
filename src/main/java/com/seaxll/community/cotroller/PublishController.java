package com.seaxll.community.cotroller;

import com.seaxll.community.mapper.QuestionMapper;
import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import com.seaxll.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: PublishController
 * Description:
 * date: 2020/4/4 16:27
 *
 * @author 张海洋
 * @since JDK 1.8
 */
@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if ("".equals(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if ("".equals(description)) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if ("".equals(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = userService.userCookieVerify(request);

        Question question = new Question(title, description, tag, user);

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        question.setCreatorId(user.getId());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
