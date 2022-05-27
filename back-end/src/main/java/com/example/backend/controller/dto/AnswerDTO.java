package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import com.example.backend.entity.Reply;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AnswerDTO {
    private String questionName;
    private int id;
    private int posterId;
    private String posterName;
    private String content;
    private Date createDate;
    private int vote;
    private int questionId;
    private List<ReplyDTO> replies;
    private int reply;
    private int mark;
    private int like;
    public AnswerDTO(Answer answer) {
        questionName = answer.getQuestion().getName();
        id = answer.getId();
        posterId = answer.getPosterId();
        posterName = answer.getPoster().getNickName();
        content = answer.getContent();
        createDate = answer.getCreateDate();
        vote = answer.getVote();
        questionId = answer.getQuestionId();
        replies = new ArrayList<>();
        reply = answer.getReplies().size();
        mark = answer.getMark();
        like = answer.getLike();
        for (Reply reply: answer.getReplies()) {
            replies.add(new ReplyDTO(reply));
        }
    }
}
