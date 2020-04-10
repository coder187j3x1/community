package com.seaxll.community.dto;

import com.seaxll.community.model.Comment;
import com.seaxll.community.model.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private Integer id;
    private String content;
    private Integer type;
    private Integer parentId;
    private Integer commentatorId;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer likeCount;
    private Integer commentCount;

    private User user;
    // 回复列表
    private List<CommentDTO> childComment;

    // 增加一个无参构造，以便 @RequestBody 可以构造此实例
    public CommentDTO() {

    }

    /********************************** Constructor with Comment  **************************************/

    public CommentDTO(Comment comment) {
        this(comment, null, null);
    }

    public CommentDTO(Comment comment, User user) {
        this(comment, user, null);
    }

    public CommentDTO(Comment comment, User user, List<CommentDTO> childComment) {
        if (comment != null) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.type = comment.getType();
            this.parentId = comment.getParentId();
            this.commentatorId = comment.getCommentatorId();
            this.gmtCreate = comment.getGmtCreate();
            this.gmtModified = comment.getGmtModified();
            this.likeCount = comment.getLikeCount();
            this.commentCount = comment.getCommentCount();
        }
        this.user = user;
        this.childComment = childComment != null ? childComment : new ArrayList<>();
    }
}
