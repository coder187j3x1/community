package com.seaxll.community.cotroller;

import com.seaxll.community.cache.HotTagCache;
import com.seaxll.community.dto.PaginationDTO;
import com.seaxll.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: IndexController
 * Description:
 * date: 2020/3/31 23:05
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;


    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PaginationDTO pagination = questionService.findPagination(page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("tags", tags);
        return "index";
    }


    @GetMapping("/search")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination;
        if (search != null && !search.isEmpty()) {
            pagination = questionService.findPaginationBySearch(search, page, size);
        } else {
            pagination = questionService.findPagination(page, size);
        }
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("tags", tags);
        return "index";
    }

    @GetMapping("/tag")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PaginationDTO pagination = questionService.findPaginationByTag(tag, page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("tags", tags);
        return "index";
    }



}
