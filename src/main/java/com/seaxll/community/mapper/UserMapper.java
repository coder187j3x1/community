package com.seaxll.community.mapper;

import com.seaxll.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ClassName: UserMapper
 * Description:
 * date: 2020/4/4 9:14
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Mapper
@Component
public interface UserMapper {
    @Insert("INSERT INTO USER(NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFY, AVATAR_URL) VALUES(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModify}, #{avatarUrl})")
    void insertUser(User user);

    @Select("SELECT * FROM USER WHERE TOKEN = #{token}")
    User finByToken(@Param("token") String token);
}
