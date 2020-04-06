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

    /**
     * 查询 用户的question 并封装成PaginationDTO
     *
     * @param page  页数
     * @param size  每一页的个数
     * @return PaginationDTO
     */
    public PaginationDTO getQuestionList(Integer page, Integer size) {
        return getQuestionList(null, page, size);
    }

    /**
     * 查询 用户的question 并封装成PaginationDTO
     *
     * @param id    用户 id；为空代表查询所有用户
     * @param page  页数
     * @param size  每一页的个数
     * @return PaginationDTO
     */
    public PaginationDTO getQuestionList(Integer id, Integer page, Integer size) {
        Integer count = questionMapper.countByCreateId(id);
        Integer totalPage = (count % size == 0) ? (count / size) : (count / size + 1);

        page = verifyPage(page, totalPage);
        // 分页查询的起始地址
        Integer offset = size * (page - 1);
        List<Question> questionList;
        if (id == null) {
            questionList = questionMapper.list(offset, size);
        } else {
            questionList = questionMapper.findQuestionByCreateId(id, offset, size);
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO pagination = new PaginationDTO();
        questionList.forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO(question);
            User user = userMapper.findUserById(question.getCreatorId());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        pagination.setQuestions(questionDTOList);

        pagination.setPagination(totalPage, page);
        return pagination;
    }

    /**
     * 验证 页码 page 的值是否正确
     * @param page 页码值
     * @param totalPage 总页数
     * @return 页码值
     */
    private Integer verifyPage(Integer page, Integer totalPage) {
        page = totalPage == 0 ? 1
                : (0 < page && page < totalPage) ? page
                        : (page >= totalPage ? totalPage : 1);
        return page;
    }

    /**
     * 通过 questionID 从数据库中查找 question 与 user 并封装成一个 QuestionDTO
     *
     * @param questionId 问题id
     * @return questionDTO
     */
    public QuestionDTO findQuestionById(Integer questionId) {
        Question question = questionMapper.findQuestionById(questionId);
        User user = userMapper.findUserById(question.getCreatorId());
        return new QuestionDTO(question, user);
    }
}
