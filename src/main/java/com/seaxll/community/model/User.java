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
    private String avatarUrl;

    public User() {

    }

    public User(String name, String accountId, String token, String avatarUrl) {
        this.name = name;
        this.accountId = accountId;
        this.token = token;
        this.avatarUrl = avatarUrl;
        this.gmtCreate = new Timestamp(System.currentTimeMillis());
        this.gmtModify = this.gmtCreate;
    }
}
