package xyz.xyzniu.forum.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xyzniu.forum.dto.QuestionDTO;
import xyz.xyzniu.forum.mapper.QuestionMapper;
import xyz.xyzniu.forum.mapper.UserMapper;
import xyz.xyzniu.forum.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    public List<QuestionDTO> list(Pagination pagination, int currentPage, int size) {
        int questionCount = (int) questionMapper.countByExample(new QuestionExample());
        pagination.init(currentPage, size, questionCount);
        
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),
                new RowBounds(pagination.getOffset(), pagination.getSize()));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size() != 0) {
                questionDTO.setUser(users.get(0));
            }
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    
}
