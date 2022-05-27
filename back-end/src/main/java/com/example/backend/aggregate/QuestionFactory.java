package com.example.backend.aggregate;

import com.example.backend.entity.Question;
import com.example.backend.repo.AnswerRepo;
import com.example.backend.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionFactory {
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    QuestionRepo questionRepo;
    public Question getQuestion(int questionId){
        Question question = questionRepo.findById(questionId).orElse(null);
        assert question != null;
        question.setAnswerRepo(answerRepo);
        return question;
    }
    public Question getQuestion(Question question) {
        question.setAnswerRepo(answerRepo);
        return question;
    }
}
