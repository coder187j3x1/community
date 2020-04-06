package com.seaxll.community.cotroller;

import com.seaxll.community.dto.CommentDTO;
import com.seaxll.community.mapper.CommentMapper;
import com.seaxll.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private CommentMapper commentMapper;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody(required = false) CommentDTO commentDTO,
                       HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentID());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        comment.setGmtModified(comment.getGmtCreate());
        // User user = (User) request.getSession().getAttribute("user");
        // comment.setCommentatorId(user.getId());
        comment.setCommentatorId(1);
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        commentMapper.insertComment(comment);
        return null;
    }
}
