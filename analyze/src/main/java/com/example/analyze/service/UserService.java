package com.example.analyze.service;

import com.example.analyze.Repo.UserRepo;
import com.example.analyze.entity.Answer;
import com.example.analyze.entity.Question;
import com.example.analyze.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Scheduled(fixedRate = 100000)
    public void calculateUserPreference() {
        List<User> users = userRepo.findAll();
        for (User user: users) {
            assert user != null;
            List<Question> votedQuestions = user.getVotedQuestions();
            List<Answer> votedAnswers = user.getVotedAnswer();
            List<Answer> markedAnswers = user.getMarkedAnswer();
            List<Answer> likedAnswers = user.getLikeAnswers();
            Set<Answer> answerSet = new HashSet<>(votedAnswers);
            answerSet.addAll(markedAnswers);
            answerSet.addAll(likedAnswers);

            HashMap<String, Integer> profileWordMap = new HashMap<>();
            for (Answer answer: answerSet) {
                try {
                    HashMap<String, Integer> wordMap
                            = new ObjectMapper().readValue(answer.getTopTenWords(), new TypeReference<>() {});
                    for (Map.Entry<String, Integer> entry: wordMap.entrySet()) {
                        if (!profileWordMap.containsKey(entry.getKey())) {
                            profileWordMap.put(entry.getKey(), entry.getValue());
                        } else {
                            profileWordMap.put(entry.getKey(), profileWordMap.get(entry.getKey()) + entry.getValue());
                        }
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            for (Question question: votedQuestions) {
                String[] keywords = question.getKeywords().split(",");
                for (String word: keywords) {
                    if (!profileWordMap.containsKey(word)) {
                        profileWordMap.put(word, 2);
                    } else {
                        profileWordMap.put(word, profileWordMap.get(word) + 2);
                    }
                }
            }

            profileWordMap = AnalysisService.sortMap(profileWordMap);
            ArrayList<String> keys = new ArrayList<>(profileWordMap.keySet());
            HashMap<String, Integer> simplifiedProfileMap = new HashMap<>();
            for (int i = 0; i < 10; i++) {
                simplifiedProfileMap.put(
                        keys.get(keys.size()-1-i),
                        profileWordMap.get(keys.get(keys.size()-1-i))
                );
            }
            try {
                String userProfile = new ObjectMapper().writeValueAsString(simplifiedProfileMap);
                user.setVector(userProfile);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            userRepo.save(user);
        }
    }
}
