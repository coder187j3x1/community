package com.seaxll.community.dto;

import lombok.Data;

import java.util.List;

/**
 * ClassName: TagDTO
 * Description:
 * date: 2020/4/11 23:19
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
