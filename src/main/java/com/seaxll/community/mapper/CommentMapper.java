package com.seaxll.community.mapper;

import com.seaxll.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * ClassName: CommentMapper
 * Description:
 * date: 2020/4/6 22:28
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Mapper
@Component
public interface CommentMapper {
    @Insert("INSERT INTO COMMENT (PARENT_ID, TYPE, COMMENTATOR_ID, GMT_CREATE, GMT_MODIFY, LIKE_COUNT, CONTENT, COMMENT_COUNT) " +
            "VALUES(#{parentId}, #{type}, #{commentatorId}, #{gmtCreate}, #{gmtModified}, #{likeCount}, #{content}, #{commentCount})")
    void insertComment(Comment comment);
}
