package com.seaxll.community.mapper;

import com.seaxll.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT * FROM QUESTION LIMIT #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT * FROM QUESTION WHERE CREATOR_ID = #{id} LIMIT #{offset}, #{size}")
    List<Question> findQuestionByCreateId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT COUNT(1) FROM QUESTION")
    Integer count();

    @Select("SELECT COUNT(1) FROM QUESTION WHERE CREATOR_ID = #{id}")
    Integer countByCreateId(@Param("id") Integer id);

}
