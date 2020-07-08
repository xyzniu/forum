package xyz.xyzniu.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xyzniu.forum.mapper.UserMapper;
import xyz.xyzniu.forum.model.User;
import xyz.xyzniu.forum.model.UserExample;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andAccountIdEqualTo(user.getAccountId());
            userMapper.updateByExampleSelective(user, userExample1);
        } else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
