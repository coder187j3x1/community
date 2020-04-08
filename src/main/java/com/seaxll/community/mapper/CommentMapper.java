package com.seaxll.community.mapper;

import com.seaxll.community.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Select("SELECT * FROM COMMENT WHERE ID = #{parentId}")
    Comment findCommentById(@Param("parentId") Integer parentId);

    @Update("UPDATE COMMENT SET COMMENT_COUNT = #{commentCount} WHERE ID = #{id}")
    void updateCommentCount(Comment comment);

    @Select("SELECT * FROM COMMENT WHERE PARENT_ID = #{questionId} AND TYPE = 0")
    List<Comment> findAllCommentByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT * FROM COMMENT WHERE PARENT_ID = #{id} AND TYPE = 1")
    List<Comment> findChildCommentByCommentId(Integer id);
}
