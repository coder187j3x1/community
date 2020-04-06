package com.seaxll.community.dto;

import lombok.Data;

/**
 * ClassName: CommentDTO
 * Description:
 * date: 2020/4/6 22:25
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class CommentDTO {
    private Long parentID;
    private String content;
    private Integer type;
}
