package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.dto.AnswerDTO;
import com.example.backend.entity.Answer;
import com.example.backend.entity.Reply;
import com.example.backend.service.AnswerService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    UserService userService;
    @PostMapping("/post")
    @ResponseBody
    public JSONObject post(@RequestBody Answer answer) {
        JSONObject res = new JSONObject();
        answerService.post(answer);
        res.put("status", 200);
        return res;
    }

    @GetMapping("/vote")
    @ResponseBody
    public JSONObject vote(@RequestParam int userId, @RequestParam int answerId) {
        JSONObject res = new JSONObject();
        boolean voteResult = userService.voteForAnswer(userId, answerId);
        if (voteResult) {
            res.put("response", "You have voted");
        } else {
            res.put("response", "You have unvoted");
        }
        return res;
    }

    @GetMapping("/like")
    @ResponseBody
    public JSONObject like(@RequestParam int userId, @RequestParam int answerId) {
        JSONObject res = new JSONObject();
        boolean likeResult = userService.likeAnswer(userId, answerId);
        if (likeResult) {
            res.put("response", "Like~");
        } else {
            res.put("response", "Dislike!");
        }
        return res;
    }
    @GetMapping("/mark")
    @ResponseBody
    public JSONObject mark(@RequestParam int userId, @RequestParam int answerId, @RequestParam String tags) {
        JSONObject res = new JSONObject();
        boolean markResult = userService.markAnswer(userId, answerId, tags);
        if (markResult) {
            res.put("response", "You have marked");
        } else {
            res.put("response", "You have unmarked");
        }
        return res;
    }

    @PostMapping("/reply")
    @ResponseBody
    public JSONObject reply(@RequestBody Reply replyRequest) {
        answerService.addReply(replyRequest);
        return null;
    }

    @GetMapping("/get")
    @ResponseBody
    public JSONObject get(@RequestParam int answerId) {
        JSONObject res = new JSONObject();
        AnswerDTO answer = new AnswerDTO(answerService.get(answerId));
        res.put("answer", answer);
        return res;
    }
}
