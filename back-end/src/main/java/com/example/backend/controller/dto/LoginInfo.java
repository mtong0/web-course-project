package com.example.backend.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginInfo {
    private int userId;
    private String nickName;
    private Date sessionId;
    private int status;
}
