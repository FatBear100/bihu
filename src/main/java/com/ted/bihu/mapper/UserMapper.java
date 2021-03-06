package com.ted.bihu.mapper;

import com.ted.bihu.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,avatar_url) VALUES (#{name}," +
            "#{accountId}, " +
            "#{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(int id);
}
