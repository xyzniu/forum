package xyz.xyzniu.forum.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xyzniu.forum.dto.QuestionDTO;
import xyz.xyzniu.forum.mapper.QuestionMapper;
import xyz.xyzniu.forum.mapper.UserMapper;
import xyz.xyzniu.forum.model.Pagination;
import xyz.xyzniu.forum.model.Question;
import xyz.xyzniu.forum.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    public List<QuestionDTO> list(Pagination pagination, int currentPage, int size) {
        int questionCount = questionMapper.count();
        pagination.init(currentPage, size, questionCount);
        
        List<Question> questionList = questionMapper.list(pagination.getOffset(), pagination.getSize());
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    
}
