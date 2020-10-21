package com.ted.bihu.mapper;

import com.ted.bihu.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,desc,gmt_create,gmt_modified,creator,tag) values (#{title},#{desc}," +
            "#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);
}
