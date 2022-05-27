package com.example.backend.service;

import com.example.backend.entity.Answer;
import com.example.backend.entity.User;
import com.example.backend.repo.AnswerRepo;
import com.example.backend.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchEngine {
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired NLIService nliService;


    public List<Answer> search(String searchString) {
        if (searchString.equals("")) {
            String regex = "'.*'";
            return answerRepo.findAnswer(regex);
        }
        Set<String> searchTokens = nliService.getSearchTokens(searchString);
        String regex = nliService.getRegex(searchTokens);
        return answerRepo.findAnswer(regex);
    }

    public List<Answer> search(String searchString, int userId) throws JsonProcessingException {
        String regex;
        System.out.println(searchString + "$");
        if (searchString.equals("")) {
            return search("");
        } else {
            Set<String> searchTokens = nliService.getSearchTokens(searchString);
            regex = nliService.getRegex(searchTokens);
        }
        List<Answer> answers = answerRepo.findAnswer(regex);
        User user = userRepo.findById(userId).orElse(null);


        for (Answer answer: answers) {
            HashMap<String, Integer> answerKeywordsMap
                    = new ObjectMapper().readValue(answer.getTopTenWords(),
                    new TypeReference<>() {
                    });
            List<String> answerKeywords = new ArrayList<>(answerKeywordsMap.keySet());
            assert user != null;
            HashMap<String, Integer> userProfile  = new ObjectMapper().readValue(
                    user.getVector(), new TypeReference<>(){});

            for (String answerWord: answerKeywords) {
                for (String userKeyword: userProfile.keySet()) {
                    if (answerWord.equals(userKeyword))
                        answer.addUserRankPoint(userProfile.get(userKeyword));
                }
            }
        }

        List<Answer> userRankedAnswers = new ArrayList<>();
        for(int i = 0; i*5 < answers.size(); i++) {
            int toIndex = i + 5;
            if (toIndex > answers.size()-1) toIndex = answers.size();
            List<Answer> subAnswers =  answers.subList(i*5, toIndex);
            userRankedAnswers.addAll(
                    subAnswers.stream()
                            .sorted((o1, o2)-> o2.getUserRankPoint()-o1.getUserRankPoint())
                            .collect(Collectors.toList())
            );
        }
        return userRankedAnswers;
    }
}
