package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import com.example.backend.entity.Reply;
import lombok.Data;

import java.util.Date;

@Data
public class AnswerQ {
    private int id;
    private int posterId;
    private String posterName;
    private String content;
    private Date createDate;
    private int vote;
    private int questionId;
    private int reply;
    private int mark;
    private int like;
    public AnswerQ(Answer answer) {
        id = answer.getId();
        posterId = answer.getPosterId();
        posterName = answer.getPoster().getNickName();
        content = answer.getContent();
        createDate = answer.getCreateDate();
        vote = answer.getVote();
        questionId = answer.getQuestionId();
        reply = answer.getReplies().size();
        mark = answer.getMark();
        like = answer.getLike();
    }
}
