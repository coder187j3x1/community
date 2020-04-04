package com.seaxll.community.mapper;

import com.seaxll.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * ClassName: QuestionMapper
 * Description:
 * date: 2020/4/4 18:24
 *
 * @author 张海洋
 * @since JDK 1.8
 */
@Mapper
@Component
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION(TITLE, DESCRIPTION, GMT_CREATE, GMT_MODIFY, CREATOR_ID, COMMENT_COUNT, VIEW_COUNT, LIKE_COUNT, TAG) " +
            "VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModify}, #{creatorId}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void insertQuestion(Question question);

}
