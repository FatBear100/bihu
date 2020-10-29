package com.ted.bihu.mapper;

import com.ted.bihu.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,desc,gmt_create,gmt_modified,creator,tag) values (#{title},#{desc}," +
            "#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(int offset, Integer size);
//    List<Question> list(@Param(value = "offset") int offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    int count();
}
