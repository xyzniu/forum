package xyz.xyzniu.forum.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.xyzniu.forum.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model) {
        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "服务冒烟了，要不你稍后再试试！！！");
        }
        return new ModelAndView("error");
    }
    
}
