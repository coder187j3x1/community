package com.seaxll.community.service;

import com.seaxll.community.mapper.CommentMapper;
import com.seaxll.community.model.Comment;
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

    public void insertComment(Comment comment) {
        // Todo：回答问题 or 回复评论
        commentMapper.insertComment(comment);
    }
}
