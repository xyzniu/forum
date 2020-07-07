package xyz.xyzniu.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.xyzniu.forum.model.Question;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    
    
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    public void insertPublish(Question question);
    
    @Select("select * from question limit #{offset}, #{size}")
    public List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);
    
    @Select("select count(1) from question")
    public Integer count();
}
