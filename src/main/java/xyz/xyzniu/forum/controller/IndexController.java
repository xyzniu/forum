package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xyzniu.forum.dto.QuestionDTO;
import xyz.xyzniu.forum.model.Pagination;
import xyz.xyzniu.forum.service.QuestionService;

import java.util.List;

@Controller
public class IndexController {
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        Model model) {
        // Question列表
        Pagination pagination = new Pagination();
        List<QuestionDTO> questionDTOList = questionService.list(pagination, page, size);
        
        model.addAttribute("questions", questionDTOList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", pagination.getPages());
        
        return "index";
    }
    
    
}
