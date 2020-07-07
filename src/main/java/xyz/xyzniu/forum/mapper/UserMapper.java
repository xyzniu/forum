package xyz.xyzniu.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.xyzniu.forum.model.User;

@Repository
@Mapper
public interface UserMapper {
    
    @Insert("insert into user" +
            "(name, account_id, token, gmt_create, gmt_modified)" +
            "values" +
            "(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);
    
    @Select("select * from user " +
            "where token = #{token}")
    User findByToken(@Param("token") String token);
    
    @Select("select * from user " +
            "where id = #{creator}")
    User findById(@Param("creator") Integer creator);
}
