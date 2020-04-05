package com.seaxll.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PaginationDTO
 * Description:
 * date: 2020/4/5 14:56
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    // 是否展示上一页
    private boolean showPrevious;
    // 是否展示第一页
    private boolean showFirstPage;
    // 是否展示下一页
    private boolean showNext;
    // 是否展示最后一页
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;
        this.pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                this.pages.add(0, page - i);
            }
            if (page + i <= this.totalPage) {
                this.pages.add(page + i);
            }
        }

        // 是否展示上一页
        this.showPrevious = page != 1;
        // 是否展示下一页
        this.showNext = page != this.totalPage;
        // 是否展示第一页
        this.showFirstPage = !this.pages.contains(1);
        // 是否展示最后一页
        this.showEndPage = !this.pages.contains(this.totalPage);
    }
}
