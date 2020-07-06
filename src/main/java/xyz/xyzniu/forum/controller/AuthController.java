package xyz.xyzniu.forum.controller;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xyzniu.forum.dto.AccessTokenDTO;
import xyz.xyzniu.forum.dto.GitHubUser;
import xyz.xyzniu.forum.provider.GitHubProvider;

import java.io.IOException;

@Controller
public class AuthController {
    
    @Autowired
    private GitHubProvider gitHubProvider;
    
    @GetMapping("/callback")
    @ResponseBody
    public GitHubUser callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setClient_id("3d61b0f7ffc60eee5c18");
        accessTokenDTO.setClient_secret("399bd0442c49e3c5ad892cfad9bc796e8bc55ec7");
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        return user;
    }
    
    
}
