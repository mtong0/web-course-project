package com.example.analyze.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private Date createDate;
    private Date modifiedDate;
    @Column(name = "poster_id")
    private int posterId;
    private int vote;
    private int rankPoint;
    private String keywords;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "poster_id", updatable = false, insertable = false)
    private User poster;

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }


    public void setCreateDate(Date createDate){
        this.createDate = createDate;
        this.modifiedDate = createDate;
    }
}
