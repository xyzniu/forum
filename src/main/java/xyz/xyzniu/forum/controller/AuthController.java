package xyz.xyzniu.forum.controller;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xyzniu.forum.dto.AccessTokenDTO;
import xyz.xyzniu.forum.dto.GitHubUser;
import xyz.xyzniu.forum.provider.GitHubProvider;

@Controller
public class AuthController {
    
    @Autowired
    private GitHubProvider gitHubProvider;
    
    @Value("${github.client.id}")
    private String clientId;
    
    @Value("${github.client.secret}")
    private String clientSecret;
    
    @Value("${github.redirect.uri}")
    private String redirectUri;
    
    
    @GetMapping("/callback")
    @ResponseBody
    public GitHubUser callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        return user;
    }
    
}
