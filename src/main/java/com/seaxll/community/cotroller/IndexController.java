package com.seaxll.community.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName: IndexController
 * Description:
 * date: 2020/3/31 23:05
 *
 * @author 张海洋
 * @since JDK 1.8
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(@RequestParam(name = "name", defaultValue = "seaxll") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
