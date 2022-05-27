package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import com.example.backend.entity.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class QuestionDTO {
    private int id;
    private int posterId;
    private String content;
    private Date createDate;
    private int vote;
    private List<AnswerQ> answers;
    private int reply;
    public QuestionDTO(Question question) {
        id = question.getId();
        posterId = question.getPosterId();
        content = question.getName();
        createDate = question.getCreateDate();
        vote = question.getVote();
        answers = new ArrayList<>();
        reply = question.getAnswers().size()-1;
        for (Answer answer: question.getAnswers()) {
            if (answer.getPosterId() != 0) {
                answers.add(new AnswerQ(answer));
            }
        }
    }
}
