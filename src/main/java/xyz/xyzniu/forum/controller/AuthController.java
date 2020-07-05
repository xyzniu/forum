package xyz.xyzniu.forum.controller;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/callback")
    @ResponseBody
    public GitHubUser callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("3d61b0f7ffc60eee5c18");
        accessTokenDTO.setClient_secret("399bd0442c49e3c5ad892cfad9bc796e8bc55ec7");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        return user;
    }
    
}
