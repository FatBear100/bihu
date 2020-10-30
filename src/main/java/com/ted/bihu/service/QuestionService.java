package com.ted.bihu.service;

import com.ted.bihu.dto.PageDTO;
import com.ted.bihu.dto.QuestionDTO;
import com.ted.bihu.mapper.QuestionMapper;
import com.ted.bihu.mapper.UserMapper;
import com.ted.bihu.model.Question;
import com.ted.bihu.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        int totalPage;
        int totalCount = questionMapper.count();
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageDTO.setPage(totalPage, page);
        int offset = size * (page - 1);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questionList = questionMapper.list(offset, size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOS);

        return pageDTO;
    }

    public PageDTO list(int userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        int totalPage;
        int totalCount = questionMapper.countByUserId(userId);
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageDTO.setPage(totalPage, page);
        int offset = size * (page - 1);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questionList = questionMapper.listByUserId(userId, offset, size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOS);

        return pageDTO;
    }
}
