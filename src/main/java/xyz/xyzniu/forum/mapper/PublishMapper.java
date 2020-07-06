package xyz.xyzniu.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.xyzniu.forum.model.Publish;

@Repository
@Mapper
public interface PublishMapper {
    
    
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    public void insertPublish(Publish publish);
}
