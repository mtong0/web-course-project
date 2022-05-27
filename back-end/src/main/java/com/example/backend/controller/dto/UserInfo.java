package com.example.backend.controller.dto;

import com.example.backend.entity.Badge;
import com.example.backend.entity.User;
import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String nickName;
    private String email;
    private Badge badge;
    private int experience;
    private String topics;

    public UserInfo(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.badge = user.getBadge();
        this.experience = user.getExperience();
        this.topics = user.getTopics();
    }
}
