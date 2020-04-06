package com.seaxll.community.cotroller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * ClassName: CommunityErrorController
 * Description:
 * date: 2020/4/6 19:22
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class CommunityErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
}
