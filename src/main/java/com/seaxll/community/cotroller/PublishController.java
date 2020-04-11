package com.seaxll.community.cotroller;

import com.seaxll.community.cache.TagCache;
import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import com.seaxll.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: PublishController
 * Description:
 * date: 2020/4/4 16:27
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
            HttpServletRequest request, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag.replaceAll(",", "|"));
        model.addAttribute("tags", TagCache.get());
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

        User user = (User) request.getSession().getAttribute("user");

        Question question = new Question(title, description, tag, user);

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (id != null) {
            question.setId(id);
        }
        question.setCreatorId(user.getId());
        questionService.updateOrInsert(question);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String publish(@PathVariable(name = "id") Integer id, Model model) {
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

}
