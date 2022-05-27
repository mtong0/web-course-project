package com.example.backend.aggregate;

import com.example.backend.entity.User;
import com.example.backend.repo.AnswerRepo;
import com.example.backend.repo.QuestionRepo;
import com.example.backend.repo.UserMarkAnswerRepo;
import com.example.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    UserMarkAnswerRepo userMarkAnswerRepo;
    public User getUser(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        assert user != null;
        user.setAnswerRepo(answerRepo);
        user.setQuestionRepo(questionRepo);
        user.setUserMarkAnswerRepo(userMarkAnswerRepo);
        return user;
    }
}
