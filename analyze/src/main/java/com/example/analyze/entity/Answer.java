package com.example.analyze.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`")
    private int id;
    @Column(name = "question_id")
    private int questionId;
    @Column(name = "poster_id")
    private int posterId;
    private Date createDate;
    private int vote;
    private String content;
    private int mark;
    @Column(name = "`like`")
    private int like;
    private int rankPoint;
    private String topTenWords;
    private String topics;

    @ManyToOne
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "poster_id", insertable = false, updatable = false)
    private User poster;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", posterId=" + posterId +
                ", createDate=" + createDate +
                ", vote=" + vote +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getId() == answer.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
