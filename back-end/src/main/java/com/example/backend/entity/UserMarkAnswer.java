package com.example.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_mark_answer")
public class UserMarkAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "answer_id")
    private int answerId;
    private String tags;
}
