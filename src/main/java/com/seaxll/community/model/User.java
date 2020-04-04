package com.seaxll.community.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: User
 * Description:
 * date: 2020/4/4 9:17
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;
}
