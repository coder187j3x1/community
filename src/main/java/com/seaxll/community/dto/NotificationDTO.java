package com.seaxll.community.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: NotificationDTO
 * Description:
 * date: 2020/4/12 8:34
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Integer notifierId;
    private Integer receiverId;
    private Integer outerId;
    private Integer type;
    private Timestamp gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String typeName;
}
