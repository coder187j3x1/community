package com.seaxll.community.service;

import com.seaxll.community.dto.PaginationDTO;
import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.mapper.QuestionMapper;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: QuestionService
 * Description:
 * date: 2020/4/5 9:59
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> getQuestionList(Integer page, Integer size) {
        // 分页查询的起始地址
        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO page = new PaginationDTO();
        questionList.forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO(question);
            User user = userMapper.findUserById(question.getId());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        return questionDTOList;
    }
}
