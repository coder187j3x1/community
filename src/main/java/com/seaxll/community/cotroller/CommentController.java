package com.seaxll.community.cotroller;

import com.seaxll.community.dto.CommentDTO;
import com.seaxll.community.dto.ResultDTO;
import com.seaxll.community.enums.ErrorCode;
import com.seaxll.community.model.Comment;
import com.seaxll.community.model.User;
import com.seaxll.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * ClassName: CommentController
 * Description:
 * date: 2020/4/6 22:20
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody(required = false) CommentDTO commentDTO,
                       HttpServletRequest request) {
        // 验证user
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(ErrorCode.PLEASE_LOGIN);
        }

        // 验证评论
        if (commentDTO == null || StringUtils.isEmpty(commentDTO.getContent())){
            return ResultDTO.errorOf(ErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentatorId(user.getId());
        comment.setCommentatorId(1);
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        
        commentService.insertComment(comment);

        return ResultDTO.okOf();
    }
}
