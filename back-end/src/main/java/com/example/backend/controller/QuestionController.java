package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.controller.dto.QuestionDTO;
import com.example.backend.entity.Question;
import com.example.backend.service.QuestionService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public JSONObject getQuestion(@PathVariable int id) {
        JSONObject res = new JSONObject();
        Question question = questionService.getQuestion(id);
        QuestionDTO questionResponse = new QuestionDTO(question);
        res.put("question", questionResponse);
        return res;
    }

    @GetMapping("/list")
    @ResponseBody
    public JSONObject getQuestions(@RequestParam int userId) {
        JSONObject res = new JSONObject();
//        if (userId == 0) {
            List<QuestionCard> questionCards = questionService.getQuestion();
            res.put("questionCards", questionCards);
//        }
        return res;
    }

    @PostMapping("/post")
    @ResponseBody
    public void post(@RequestBody Question req){
        questionService.postQuestion(req);
    }

    @GetMapping("/vote")
    @ResponseBody
    public JSONObject vote(@RequestParam int userId, @RequestParam int questionId) {
        JSONObject res = new JSONObject();
        boolean voteResult = userService.voteQuestion(userId, questionId);
        if (voteResult) {
            res.put("response", "You have voted the question");
        } else {
            res.put("response", "You have unVoted the question");
        }

        return res;
    }
}
