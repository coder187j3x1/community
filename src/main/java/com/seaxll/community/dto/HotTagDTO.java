package com.seaxll.community.dto;

import lombok.Data;

/**
 * ClassName: HotTagDTO
 * Description:
 * date: 2020/4/12 14:21
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
