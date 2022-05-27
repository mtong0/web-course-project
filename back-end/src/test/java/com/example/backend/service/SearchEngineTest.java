package com.example.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchEngineTest {

    @Autowired
    SearchEngine searchEngine;

    @Test
    void search() {
        try {
            searchEngine.search("vaccine animal", 10);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}