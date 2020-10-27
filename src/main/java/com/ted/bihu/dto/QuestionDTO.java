package com.ted.bihu.dto;

import com.ted.bihu.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private int id;
    private String title;
    private String desc;
    private long gmtCreate;
    private long gmtModified;
    private String tag;
    private int creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private User user;
}
