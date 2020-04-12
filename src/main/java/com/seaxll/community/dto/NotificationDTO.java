package com.seaxll.community.dto;

import lombok.Data;

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
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
