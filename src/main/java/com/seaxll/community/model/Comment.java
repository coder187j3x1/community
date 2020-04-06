package com.seaxll.community.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: Comment
 * Description:
 * date: 2020/4/6 22:29
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentatorId;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
}
