package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.dto.LoginInfo;
import com.example.backend.controller.dto.MarkedAnswer;
import com.example.backend.controller.dto.UserInfo;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject req){
        JSONObject res = new JSONObject();
        String email = req.getString("username");
        String password = req.getString("password");
        LoginInfo loginInfo = userService.login(email, password);
        res.put("loginInfo", loginInfo);
        return res;
    }

    @GetMapping("/marked-answers")
    @ResponseBody
    public JSONObject getMarkedAnswers(@RequestParam int userId) {
        JSONObject res = new JSONObject();
        List<MarkedAnswer> markedAnswers = userService.getMarkedAnswers(userId);
        res.put("markedAnswers", markedAnswers);
        return res;
    }

    @PostMapping("/signup")
    @ResponseBody
    public JSONObject signup(@RequestBody User user) {
        JSONObject res = new JSONObject();
        userService.signUp(user);
        return res;
    }

    @GetMapping("/info")
    @ResponseBody
    public JSONObject getInfo(@RequestParam int userId) {
        JSONObject res = new JSONObject();
        UserInfo userInfo = userService.getUserInfo(userId);
        res.put("userInfo", userInfo);
        return  res;
    }
}
