package com.seaxll.community.mapper;

import com.seaxll.community.model.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: NotificationMapper
 * Description:
 * date: 2020/4/12 8:34
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Mapper
@Component
public interface NotificationMapper {

    @Select("SELECT COUNT(1) FROM NOTIFICATION")
    Integer count(Integer userId);

    @Select("SELECT * FROM Notification WHERE RECEIVER_ID = #{id} LIMIT #{offset}, #{size}")
    List<Notification> findNotificationByUserId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT COUNT(1) FROM NOTIFICATION WHERE STATUS = 0")
    Integer unreadCount(Integer userId);

    @Select("SELECT * FROM Notification WHERE ID = #{is}")
    Notification selectById(Integer id);

    @Update("UPDATE NOTIFICATION SET STATUS = ${status} WHERE ID = #{id}")
    void updateNotificationById(Notification notification);

    @Insert("INSERT INTO NOTIFICATION(NOTIFIER_ID, RECEIVER_ID, OUTER_ID, TYPE, GMT_CREATE, STATUS, NOTIFIER_NAME, OUTER_TITLE) " +
            "VALUES(#{notifierId}, #{receiverId}, #{outerId}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insertNotification(Notification notification);
}
