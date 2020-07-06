package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xyzniu.forum.mapper.PublishMapper;
import xyz.xyzniu.forum.model.Publish;
import xyz.xyzniu.forum.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    
    @Autowired
    private PublishMapper publishMapper;
    
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
        
        Publish publish = new Publish();
        publish.setTitle(title);
        publish.setDescription(description);
        publish.setTag(tag);
        publish.setCreator(user.getId());
        publish.setGmtCreate(System.currentTimeMillis());
        publish.setGmtModified(publish.getGmtCreate());
        
        publishMapper.insertPublish(publish);
        
        return "redirect:/";
    }
    
}
