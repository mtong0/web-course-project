package com.example.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "poster_id")
    private int posterId;
    @Column(name = "answer_id")
    private int answerId;
    @Column(name = "`like`")
    private int like;
    private String content;
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "answer_id", insertable = false, updatable = false)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "poster_id", insertable = false, updatable = false)
    private User poster;
}
