package com.seaxll.community.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName: AuthorizeController
 * Description:
 * date: 2020/4/1 23:01
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class AuthorizeController {
    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state) {

        return "index";
    }
}
