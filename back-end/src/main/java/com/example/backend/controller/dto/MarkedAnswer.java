package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import com.example.backend.entity.UserMarkAnswer;
import lombok.Data;

import java.util.Date;

@Data
public class MarkedAnswer {
    private int id;
    private String question;
    private int questionId;
    private String content;
    private String posterName;
    private String tags;
    private Date createDate;
    public MarkedAnswer(Answer answer, UserMarkAnswer userMarkAnswer) {
        questionId = answer.getQuestionId();
        id = answer.getId();
        question = answer.getQuestion().getName();
        content = answer.getContent();
        posterName = answer.getPoster().getNickName();
        tags = userMarkAnswer.getTags();
        createDate = answer.getCreateDate();
    }
}
