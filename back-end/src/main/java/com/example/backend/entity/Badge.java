package com.example.backend.entity;

import lombok.Data;

@Data
public class Badge {
    int forNextLevel;
    String name;

    public Badge(int forNextLevel, String name) {
        this.forNextLevel = forNextLevel;
        this.name = name;
    }
}
