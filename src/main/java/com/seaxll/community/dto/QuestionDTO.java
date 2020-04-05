package com.seaxll.community.dto;

import com.seaxll.community.model.Question;
import com.seaxll.community.model.User;
import lombok.Data;

import java.sql.Timestamp;

/**
 * ClassName: QuestionDTO
 * Description:
 * date: 2020/4/5 9:51
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class QuestionDTO {
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
    private User user;

    public QuestionDTO(Question question) {
        this(question, null);
    }

    public QuestionDTO(Question question, User user) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.description = question.getDescription();
        this.gmtCreate = question.getGmtCreate();
        this.gmtModify = question.getGmtModify();
        this.creatorId = question.getCreatorId();
        this.commentCount = question.getCommentCount();
        this.viewCount = question.getViewCount();
        this.likeCount = question.getLikeCount();
        this.tag = question.getTag();
        this.user = user;
    }
}
