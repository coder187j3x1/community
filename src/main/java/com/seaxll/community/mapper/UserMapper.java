package com.seaxll.community.mapper;

import com.seaxll.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserMapper
 * Description:
 * date: 2020/4/4 9:14
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USER(NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFY) VALUES(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModify})")
    void insertUser(User user);

}
