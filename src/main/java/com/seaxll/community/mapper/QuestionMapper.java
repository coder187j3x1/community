package com.seaxll.community.mapper;

import com.seaxll.community.dto.QuestionDTO;
import com.seaxll.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * ClassName: QuestionMapper
 * Description:
 * date: 2020/4/4 18:24
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Mapper
@Component
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION(TITLE, DESCRIPTION, GMT_CREATE, GMT_MODIFY, CREATOR_ID, COMMENT_COUNT, VIEW_COUNT, LIKE_COUNT, TAG) " +
            "VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModify}, #{creatorId}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void insertQuestion(Question question);

    @Select("SELECT * FROM QUESTION  ORDER BY GMT_MODIFY DESC LIMIT #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT * FROM QUESTION WHERE CREATOR_ID = #{id} ORDER BY GMT_MODIFY DESC LIMIT #{offset}, #{size}")
    List<Question> findQuestionByCreateId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT COUNT(1) FROM QUESTION")
    Integer count();

    @Select("SELECT COUNT(1) FROM QUESTION WHERE CREATOR_ID = #{id}")
    Integer countByCreateId(@Param("id") Integer id);

    @Select("SELECT * FROM QUESTION WHERE ID = #{id}")
    Question findQuestionById(@Param("id") Integer id);

    @Update("UPDATE QUESTION SET TITLE = #{title}, DESCRIPTION = #{description}, GMT_MODIFY = #{gmtModify}, TAG = #{tag} WHERE id = #{id}")
    void updateQuestion(Question question);

    @Update("UPDATE QUESTION SET VIEW_COUNT = #{viewCount} WHERE id = #{questionId}")
    void updateQuestionViewCount(@Param("questionId") Integer questionId, @Param("viewCount") Integer viewCount);

    @Update("UPDATE QUESTION SET COMMENT_COUNT = #{commentCount} WHERE id = #{id}")
    void updateCommentCount(Question question);

    @Select("SELECT * FROM QUESTION WHERE ID != #{id} AND TAG REGEXP #{tag} ORDER BY GMT_MODIFY DESC")
    List<Question> selectRelateQuestionByTag(QuestionDTO questionDTO);

    @Select("SELECT * FROM QUESTION WHERE TITLE REGEXP #{tag} ORDER BY GMT_MODIFY DESC")
    List<Question> selectQuestionBySearch(String search);

    @Select("SELECT * FROM QUESTION WHERE TAG REGEXP #{tag} ORDER BY GMT_MODIFY DESC")
    List<Question> selectQuestionByTag(String tag);
}
