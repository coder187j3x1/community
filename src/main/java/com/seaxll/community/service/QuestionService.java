package com.seaxll.community.service;

import com.seaxll.community.dto.CommentDTO;
import com.seaxll.community.dto.PaginationDTO;
import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.enums.ErrorCode;
import com.seaxll.community.exception.CommunityException;
import com.seaxll.community.mapper.CommentMapper;
import com.seaxll.community.mapper.QuestionMapper;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.Comment;
import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

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
        // id 为空则计算总问题数，id 不为空 则计算 用户的总数
        Integer count;
        // count = (id == null) ? questionMapper.count() : questionMapper.countByCreateId(id);
        User user = null;
        if (id == null) {
            count = questionMapper.count();
        } else {
            user = userMapper.findUserById(id);
            if (user == null) {
                throw new CommunityException(ErrorCode.USER_NOT_FOUND);
            }
            count = questionMapper.countByCreateId(id);
        }

        // 计算总页数
        Integer totalPage = (count % size == 0) ? (count / size) : (count / size + 1);

        page = verifyPage(page, totalPage);
        // 分页查询的起始地址
        Integer offset = size * (page - 1);
        // 查询 question list
        List<Question> questionList = (id == null)
                ? questionMapper.list(offset, size)
                : questionMapper.findQuestionByCreateId(id, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO pagination = new PaginationDTO();
        // 构造 QuestionDTO
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO(question);
            // 判断 id，减少查找次数
            user = (id != null) ? user : userMapper.findUserById(question.getCreatorId());
            if (user == null) {
                throw new CommunityException(ErrorCode.USER_NOT_FOUND);
            }
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
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
    public QuestionDTO getQuestionById(Integer questionId) {
        Question question = questionMapper.findQuestionById(questionId);
        if (question == null) {
            throw new CommunityException(ErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findUserById(question.getCreatorId());
        if (user == null) {
            throw new CommunityException(ErrorCode.USER_NOT_FOUND);
        }
        return new QuestionDTO(question, user);
    }

    /**
     *  更新 question 如果没有，就插入一条 question 记录
     * @param question question
     */
    public void updateOrInsert(Question question) {
        if (question.getId() != null) {
            // TODO: 这里可以处理一下 question.id 在数据库中 不存在的异常
            questionMapper.updateQuestion(question);
        } else {
            questionMapper.insertQuestion(question);
        }
    }

    /**
     * 更新访问量
     *
     * @param questionId id
     * @param viewCount 访问量
     */
    public void addViewCount(Integer questionId, Integer viewCount) {
        // TODO： 解决 刷新之后时间更改问题
        questionMapper.updateQuestionViewCount(questionId, viewCount);
    }

    /**
     * 根据问题 id 查询评论列表，并构造为 List<CommentDTO>
     *
     * @param id 问题 id
     * @return List<CommentDTO>
     */
    public List<CommentDTO> findCommentById(Integer id) {
        List<Comment> commentList = commentMapper.findAllCommentByQuestionId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            if (StringUtils.isEmpty(comment.getContent())) {
                throw new CommunityException(ErrorCode.COMMENT_IS_EMPTY);
            }
            User user = userMapper.findUserById(comment.getCommentatorId());
            CommentDTO commentDTO = new CommentDTO(comment, user);
            if (user == null) {
                throw new CommunityException(ErrorCode.USER_NOT_FOUND);
            }
            List<CommentDTO> childComments = commentService.findChildCommentsById(commentDTO.getId());
            // 给 childComments 按时间排序
            childComments = childComments.stream().sorted(Comparator.comparing(CommentDTO::getGmtCreate)).collect(Collectors.toList());
            commentDTO.setChildComment(childComments);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    /**
     * 查找相关问题
     * @param questionDTO
     *      QuestionDTO
     * @return
     *      List<QuestionDTO>
     */
    public List<QuestionDTO> findRelateQuestionByTag(QuestionDTO questionDTO) {
        List<QuestionDTO> relatedQuestions = new ArrayList<>();
        List<Question> questions = questionMapper.selectRelateQuestionByTag(questionDTO);
        questions.forEach(question -> relatedQuestions.add(new QuestionDTO(question)));
        return relatedQuestions;
    }
}
