package xyz.xyzniu.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xyzniu.forum.dto.AccessTokenDTO;
import xyz.xyzniu.forum.dto.GitHubUser;
import xyz.xyzniu.forum.mapper.UserMapper;
import xyz.xyzniu.forum.model.User;
import xyz.xyzniu.forum.provider.GitHubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.CollationKey;
import java.util.UUID;

@Controller
public class CallbackController {
    
    @Autowired
    private GitHubProvider gitHubProvider;
    
    @Value("${github.client.id}")
    private String clientId;
    
    @Value("${github.client.secret}")
    private String clientSecret;
    
    @Value("${github.redirect.uri}")
    private String redirectUri;
    
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/callback")
    public String callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser != null) {
            // 登陆成功
            User user = new User();
            user.setAccountId(gitHubUser.getId());
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            
            response.addCookie(new Cookie("token", token));
            
            return "redirect:/";
        } else {
            //登陆失败
            return "redirect:/";
        }
        
    }
}
