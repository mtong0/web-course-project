package com.example.backend.service;

import com.example.backend.aggregate.UserFactory;
import com.example.backend.controller.dto.LoginInfo;
import com.example.backend.controller.dto.MarkedAnswer;
import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.controller.dto.UserInfo;
import com.example.backend.entity.User;
import com.example.backend.entity.UserMarkAnswer;
import com.example.backend.repo.UserMarkAnswerRepo;
import com.example.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserFactory userFactory;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserMarkAnswerRepo userMarkAnswerRepo;

    @Transactional
    public boolean voteForAnswer(int userId, int answerId) {
        User user = userFactory.getUser(userId);
        boolean isVoted = user.voteForAnswer(answerId);
        userRepo.save(user);
        return isVoted;
    }

    @Transactional
    public boolean markAnswer(int userId, int answerId, String tags) {
        User user = userFactory.getUser(userId);
        boolean isMarked = user.markAnswer(answerId);
        userRepo.save(user);
        if (isMarked) {
            UserMarkAnswer userMarkAnswer = userMarkAnswerRepo.findByUserIdAndAnswerId(userId, answerId);
            userMarkAnswer.setTags(tags);
            userMarkAnswerRepo.save(userMarkAnswer);
        }
        return isMarked;
    }

    @Transactional
    public boolean likeAnswer(int userId, int answerId) {
        User user = userFactory.getUser(userId);
        System.out.println(userId);
        System.out.println(answerId);
        boolean isLiked = user.likeAnswer(answerId);
        userRepo.save(user);
        return isLiked;
    }

    @Transactional
    public boolean voteQuestion(int userId, int questionId) {
        User user = userFactory.getUser(userId);
        boolean isVoted = user.voteQuestion(questionId);
        userRepo.save(user);
        return isVoted;
    }

    public LoginInfo login(String email, String password) {
        LoginInfo loginInfo = new LoginInfo();
        User user = userRepo.findByEmail(email);
        if(user == null) {
            loginInfo.setStatus(204);
            return loginInfo;
        }
        if(!user.getPassword().equals(password)) {
            loginInfo.setStatus(401);
            return loginInfo;
        }
        loginInfo.setUserId(user.getId());
        loginInfo.setNickName(user.getNickName());
        loginInfo.setStatus(200);
        return loginInfo;
    }

    public List<MarkedAnswer> getMarkedAnswers(int userId) {
        User user = userFactory.getUser(userId);
        return user.getMarkedAnswer();
    }

    public void signUp(User user) {
        userRepo.save(user);
    }

    public UserInfo getUserInfo(int userId){
        return new UserInfo(userFactory.getUser(userId));
    }
}
