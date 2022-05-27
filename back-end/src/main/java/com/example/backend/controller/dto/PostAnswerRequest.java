package com.example.backend.controller.dto;

import com.example.backend.entity.Answer;
import lombok.Data;

@Data
public class PostAnswerRequest {
    private Answer answer;
}
