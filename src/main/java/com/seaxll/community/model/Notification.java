package com.seaxll.community.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: Notification
 * Description:
 * date: 2020/4/12 8:09
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class Notification {
    private Integer id;
    private Integer notifierId;
    private Integer receiverId;
    private Integer outerId;
    private Integer type;
    private Timestamp gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
