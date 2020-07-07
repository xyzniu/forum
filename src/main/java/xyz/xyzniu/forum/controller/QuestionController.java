package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xyzniu.forum.mapper.QuestionMapper;
import xyz.xyzniu.forum.model.Question;
import xyz.xyzniu.forum.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @GetMapping("/publish")
    public String publish() {
        return "question";
    }
    
    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "tag", required = false) String tag,
                          Model model,
                          HttpServletRequest request) {
        if (title == null || title.equals("")) {
            model.addAttribute("error", "请输入问题标题");
            return "question";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "请输入问题描述");
            return "question";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "请输入标签");
            return "question";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            model.addAttribute("error", "请登陆后再试");
            return "question";
        }
        
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        
        questionMapper.insertPublish(question);
        
        return "redirect:/";
    }
    
}
