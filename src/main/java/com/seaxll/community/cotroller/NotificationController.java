package com.seaxll.community.cotroller;

import com.seaxll.community.dto.NotificationDTO;
import com.seaxll.community.enums.NotificationType;
import com.seaxll.community.model.User;
import com.seaxll.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: NotificationController
 * Description:
 * date: 2020/4/12 10:15
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Integer id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationType.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationType.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}