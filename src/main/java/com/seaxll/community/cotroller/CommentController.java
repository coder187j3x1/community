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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * ClassName: CommentController
 * Description:
 *  前端 ajax 请求时 contentType:'application/json', dataType: "json", type: "POST", 的
 *      json 格式（contentType）参数，返回 json（dataType） 格式 的 post（type）请求
 *  服务器需要返回一个 json 数据类型 ，使用 @RequestMapping映射请求路径，并且需要在处理的方法上加上 @ResponseBody
 *      @RequestMapping 处理 get 或 post 请求；@GetMapping 只能处理 get 请求
 *      @ResponseBody 将返回的 java 对象转为 json 格式的数据
 * date: 2020/4/6 22:20
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 加上@ResponseBody  将java对象转为json格式的数据
    /**
     * 评论
     */
    @ResponseBody
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
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        
        commentService.insertComment(comment, user);

        return ResultDTO.okOf();
    }

    /**
     * 二级评论
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id) {
        List<CommentDTO> commentDTOS = commentService.findChildCommentsById(id);
        return ResultDTO.okOf(commentDTOS);
    }
}
