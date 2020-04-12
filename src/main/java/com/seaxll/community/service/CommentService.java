package com.seaxll.community.service;

import com.seaxll.community.dto.CommentDTO;
import com.seaxll.community.enums.CommentType;
import com.seaxll.community.enums.ErrorCode;
import com.seaxll.community.enums.NotificationStatus;
import com.seaxll.community.enums.NotificationType;
import com.seaxll.community.exception.CommunityException;
import com.seaxll.community.mapper.CommentMapper;
import com.seaxll.community.mapper.NotificationMapper;
import com.seaxll.community.mapper.QuestionMapper;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.Comment;
import com.seaxll.community.model.Notification;
import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insertComment(Comment comment, User commentator) {
        boolean typeExist = CommentType.isCommentTypeExist(comment.getType());
        if (!typeExist) {
            throw new CommunityException(ErrorCode.COMMENT_TYPE_NOT_FOUND);
        }
        if (comment.getType().equals(CommentType.COMMENT.getType())) {
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
            // 创建通知
            createNotify(comment, parentComment.getCommentatorId(), commentator.getName(), parentComment.getContent(), NotificationType.REPLY_COMMENT, parentComment.getId());
        } else {
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
            // 创建通知
            createNotify(comment, question.getCreatorId(), commentator.getName(), question.getTitle(), NotificationType.REPLY_QUESTION, question.getId());
        }
    }

    /**
     * 根据评论 id 查询二级评论（回复）， 递归
     *
     * @param id 评论的id
     * @return  commentDTO
     */
    public List<CommentDTO> findChildCommentsById(Integer id) {
        List<Comment> comments = commentMapper.findChildCommentByCommentId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            if (StringUtils.isEmpty(comment.getContent())) {
                throw new CommunityException(ErrorCode.COMMENT_IS_EMPTY);
            }
            User user = userMapper.findUserById(comment.getCommentatorId());
            if (user == null) {
                throw new CommunityException(ErrorCode.USER_NOT_FOUND);
            }
            // 这里暂时只不为 commentDTO 添加 childComment ，只查询 回复
            CommentDTO commentDTO = new CommentDTO(comment, user);
            // 回复的谁？
            commentDTO.setParentUser(userMapper.findUserById(commentDTO.getParentId()));
            commentDTOList.add(commentDTO);
            // 回复
            if (commentDTO.getCommentCount() > 0) {
                commentDTOList.addAll(Objects.requireNonNull(findChildCommentsById(commentDTO.getId())));
            }
        }
        return commentDTOList;
    }

    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationType notificationType, Integer outerId) {
        if (receiver.equals(comment.getCommentatorId())) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifierId(comment.getCommentatorId());
        notification.setStatus(NotificationStatus.UNREAD.getStatus());
        notification.setReceiverId(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insertNotification(notification);
    }
}
