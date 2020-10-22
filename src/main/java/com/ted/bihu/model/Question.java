package com.ted.bihu.model;

import lombok.Data;

@Data
public class Question {
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

}
