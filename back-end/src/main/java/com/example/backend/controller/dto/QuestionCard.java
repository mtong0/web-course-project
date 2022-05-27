package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import com.example.backend.entity.Question;
import lombok.Data;

@Data
public class QuestionCard {
    private int questionId;
    private String content;
    private int questionVoteNum;
    private int answerId;
    private String answerNickName;
    private String answerContent;
    private int answerVoteNum;
    private int answerReplyNum;
    private String userTag;

    public QuestionCard(){};
    public QuestionCard(int questionId, String content){
        this.questionId = questionId;
        this.content = content;
    }

    public QuestionCard(Question question, Answer answer) {
        this.questionId = question.getId();
        this.content = question.getName();
        this.answerId = answer.getId();
        this.answerNickName = answer.getPoster().getNickName();
        this.answerContent = answer.getContent();
        this.answerVoteNum = answer.getVote();
        this.answerReplyNum  = answer.getReplies().size();
        this.questionVoteNum = question.getVote();
    }

    public QuestionCard(Question question, Answer answer, String tag) {
        this.questionId = question.getId();
        this.content = question.getName();
        this.answerId = answer.getId();
        this.answerNickName = answer.getPoster().getNickName();
        this.answerContent = answer.getContent();
        this.answerVoteNum = answer.getVote();
        this.answerReplyNum  = answer.getReplies().size();
        this.questionVoteNum = question.getVote();
        this.userTag = tag;
    }
}
