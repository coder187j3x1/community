package com.seaxll.community.service;

import com.seaxll.community.enums.CommentType;
import com.seaxll.community.enums.ErrorCode;
import com.seaxll.community.exception.CommunityException;
import com.seaxll.community.mapper.CommentMapper;
import com.seaxll.community.mapper.QuestionMapper;
import com.seaxll.community.model.Comment;
import com.seaxll.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: CommentService
 * Description:
 * date: 2020/4/7 23:02
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void insertComment(Comment comment) {
        boolean typeExist = CommentType.isCommentTypeExist(comment.getType());
        if (!typeExist) {
            throw new CommunityException(ErrorCode.COMMENT_TYPE_NOT_FOUND);
        }
        if (comment.getType().equals(CommentType.COMMENT.getType())) {
            // TODO： 回复评论
            // 1.判断回复的评论是否存在
            Comment parentComment = commentMapper.findCommentById(comment.getParentId());
            if (parentComment == null) {
                throw new CommunityException(ErrorCode.COMMENT_NOT_FOUND);
            }
            // 2.插入一条评论
            commentMapper.insertComment(comment);

            // 3.增加评论的评论数
            parentComment.setCommentCount(parentComment.getCommentCount() + 1);
            commentMapper.updateCommentCount(parentComment);
        } else {
            // TODO：回答问题
            // 1.判断回答的问题是否存在
            Question question = questionMapper.findQuestionById(comment.getParentId());
            if (question == null) {
                throw new CommunityException(ErrorCode.QUESTION_NOT_FOUND);
            }
            // 2.插入一条评论
            commentMapper.insertComment(comment);

            // 3.增加问题评论数
            question.setCommentCount(question.getCommentCount() + 1);
            questionMapper.updateCommentCount(question);
        }
        commentMapper.insertComment(comment);
    }
}
