package com.seaxll.community.dto;


import lombok.Data;

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
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages;
}
