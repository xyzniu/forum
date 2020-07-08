package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xyzniu.forum.model.Question;
import xyz.xyzniu.forum.model.User;
import xyz.xyzniu.forum.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    
    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "tag", required = false) String tag,
                          Model model,
                          HttpServletRequest request) {
        return publish(title, description, tag, null, model, request);
    }
    
    @PostMapping("/publish/{questionId}")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "tag", required = false) String tag,
                          @PathVariable(value = "questionId", required = false) Integer questionId,
                          Model model,
                          HttpServletRequest request) {
        if (title == null || title.equals("")) {
            model.addAttribute("error", "请输入问题标题");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "请输入问题描述");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "请输入标签");
            return "publish";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            model.addAttribute("error", "请登陆后再试");
            return "publish";
        }
        
        
        if (questionId != null) {
            Question oldQuestion = questionService.findById(questionId);
            if (oldQuestion.getCreator() != user.getId()) {
                model.addAttribute("error", "您无权修改此问题");
                return "publish";
            }
        }
        
        Question question = new Question();
        question.setId(questionId);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        
        questionService.createOrUpdate(question, questionId == null);
        
        return "redirect:/";
    }
    
    @GetMapping("/publish/{questionId}")
    public String updateById(@PathVariable("questionId") Integer questionId,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        Question question = questionService.findById(questionId);
        if (question != null) {
            if (question.getCreator() != user.getId()) {
                model.addAttribute("error", "这不是您发布的问题，请重试");
                return "/question/" + questionId;
            } else {
                model.addAttribute("question", question);
                return "publish";
            }
        } else {
            return "redirect:/";
        }
    }
    
    
    @GetMapping("/question/{questionId}")
    public String question(@PathVariable("questionId") Integer questionId,
                           Model model) {
        Question question = questionService.findById(questionId);
        if (question != null) {
            model.addAttribute("question", question);
            return "question";
        } else {
            return "redirect:/";
        }
    }
    
}
