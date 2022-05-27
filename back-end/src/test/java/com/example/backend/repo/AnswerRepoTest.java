package com.example.backend.repo;

import com.example.backend.service.NLIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerRepoTest {
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    NLIService nliService;
    @Test
    void findAnswer() {
        Set<String> stringSet = new HashSet<>(Arrays.asList("food"));
        String reg = nliService.getRegex(stringSet);
        System.out.println(answerRepo.findAnswer(reg));
    }
}