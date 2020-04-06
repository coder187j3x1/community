package com.seaxll.community.cotroller;

import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: QuestionController
 * Description:
 * date: 2020/4/6 9:36
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id", required = false) String id, Model model) {
        Integer questionId = null;
        try {
            questionId = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QuestionDTO questionDTO = questionService.findQuestionById(questionId);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
