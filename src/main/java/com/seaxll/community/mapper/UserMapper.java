package com.seaxll.community.mapper;

import com.seaxll.community.model.User;
import org.apache.ibatis.annotations.*;
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
    User findUserByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select("SELECT * FROM USER WHERE ACCOUNT_ID = #{id}")
    User findUserByAccountId(@Param("id") long id);

    @Update("UPDATE USER SET NAME = #{name}, TOKEN = #{token}, GMT_MODIFY = #{gmtModify}, AVATAR_URL = #{avatarUrl} WHERE id = #{id}")
    void updateUser(User user);
}
