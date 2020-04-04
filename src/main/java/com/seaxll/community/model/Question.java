package com.seaxll.community.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: Question
 * Description:
 * date: 2020/4/4 18:25
 *
 * @author 张海洋
 * @since JDK 1.8
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;
    private Integer creatorId;
    private Integer commentCount = 0;
    private Integer viewCount = 0;
    private Integer likeCount = 0;
    private String tag;
}
