package com.example.backend.service;

import com.example.backend.aggregate.UserFactory;
import com.example.backend.entity.Answer;
import com.example.backend.entity.Reply;
import com.example.backend.entity.User;
import com.example.backend.repo.AnswerRepo;
import com.example.backend.repo.ReplyRepo;
import com.example.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AnswerService {
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    UserFactory userFactory;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ReplyRepo replyRepo;

    @Transactional
    public void post(Answer answer) {
        answer.setCreateDate(new Date());
        answerRepo.save(answer);
        User user = userFactory.getUser(answer.getPosterId());
        user.addExperience(100);
        userRepo.save(user);
    }

    public void addReply(Reply reply) {
        reply.setCreateDate(new Date());
        replyRepo.save(reply);
        User user = userFactory.getUser(reply.getPosterId());
        user.addExperience(20);
        userRepo.save(user);
    }

    public Answer get(int answerId) {
        return answerRepo.findById(answerId).orElse(null);
    }
}
