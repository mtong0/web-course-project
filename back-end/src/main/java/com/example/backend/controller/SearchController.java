package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.entity.Answer;
import com.example.backend.service.QuestionService;
import com.example.backend.service.SearchEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchEngine searchEngine;
    @Autowired
    QuestionService questionService;

    @PostMapping("/normalSearch")
    @ResponseBody
    public JSONObject search(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();
        String searchString = req.getString("searchString");
        int userId = req.getInteger("userId");
        try {
            List<Answer> answers;
            if (userId != 0) {
                answers = searchEngine.search(searchString, userId);
            } else {
                answers = searchEngine.search(searchString);
            }
            List<QuestionCard> questionCards = questionService.getQuestion(answers);
            res.put("questionCards", questionCards);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
