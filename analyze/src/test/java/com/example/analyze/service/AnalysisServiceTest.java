package com.example.analyze.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnalysisServiceTest {
    @Autowired
    AnalysisService analysisService;

    @Test
    void simplifiedAnswer() {
       analysisService.simplifiedAnswer();
    }

    @Test
    void setQuestionKeywords() {
        analysisService.setQuestionKeywords();
    }
}