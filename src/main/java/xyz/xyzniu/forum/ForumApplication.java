package xyz.xyzniu.forum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "xyz.xyzniu.forum.mapper")
public class ForumApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
    
}
