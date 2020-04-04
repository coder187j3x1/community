package com.seaxll.community.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName: PublishController
 * Description:
 * date: 2020/4/4 16:27
 *
 * @author 张海洋
 * @since JDK 1.8
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
