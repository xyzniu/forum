package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xyzniu.forum.dto.QuestionDTO;
import xyz.xyzniu.forum.mapper.UserMapper;
import xyz.xyzniu.forum.model.Pagination;
import xyz.xyzniu.forum.model.User;
import xyz.xyzniu.forum.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        HttpServletRequest request,
                        Model model) {
        
        // 用户数据
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        
        // Question列表
        Pagination pagination = new Pagination();
        List<QuestionDTO> questionDTOList = questionService.list(pagination, page, size);
        
        model.addAttribute("questions", questionDTOList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", pagination.getPages());
        
        return "index";
    }
    
    
}
