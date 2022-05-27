package com.example.backend.service;

import com.example.backend.aggregate.QuestionFactory;
import com.example.backend.aggregate.UserFactory;
import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.entity.Answer;
import com.example.backend.entity.Question;
import com.example.backend.entity.User;
import com.example.backend.repo.QuestionRepo;
import com.example.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuestionFactory questionFactory;
    @Autowired
    UserFactory userFactory;
    @Autowired
    UserRepo userRepo;

    @Transactional
    public void postQuestion(Question question) {
        System.out.println(question);
        question.setCreateDate(new Date());
        questionRepo.save(question);
        User user = userFactory.getUser(question.getPosterId());
        user.addExperience(50);
        userRepo.save(user);
    }

    public void editQuestion(Question question) {
        question.setModifiedDate(new Date());
        questionRepo.save(question);
    }

    public Question getQuestion(int questionId) {
        return questionRepo.findById(questionId).orElse(null);
    }

    public void postAnswer(int questionId, Answer answer) {
        Question question = questionRepo.findById(questionId).orElse(null);
        assert question != null;
        question.addAnswer(answer);
        questionRepo.save(question);
    }

    public List<Answer> getAnswers(int questionId, String type) {
        Question question = questionFactory.getQuestion(questionId);
        return question.getAnswers(type);
    }

    public List<QuestionCard> getQuestion() {
        List<Question> questions = questionRepo.findAllByNameLikeOrderByVoteDesc("%");
        List<QuestionCard> questionCards = new ArrayList<>();
        for (Question question: questions) {
            question = questionFactory.getQuestion(question);
            questionCards.add(question.getQuestionCard());
        }
        return questionCards;
    }

    public List<QuestionCard> getQuestion(List<Answer> answers) {
        List<QuestionCard> questionCards = new ArrayList<>();
        for (Answer answer: answers) {
            Question question = questionFactory.getQuestion(answer.getQuestionId());
            questionCards.add(question.getQuestionCard(answer));
        }
        return questionCards;
    }
}
