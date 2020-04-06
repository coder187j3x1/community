package com.seaxll.community.advice;

import com.seaxll.community.exception.CommunityException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: CustomizeExceptionHandler
 * Description:
 * date: 2020/4/6 18:17
 *
 * @author seaxll
 * @since JDK 1.8
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        if (ex instanceof CommunityException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "抱歉，您的访问出现异常，请稍后再试");
        }
        return new ModelAndView("/error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return null;
    }
}