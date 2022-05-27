package com.example.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
    private String topTenWords = "{}";
    @Transient
    private int userRankPoint;

    @ManyToOne
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "poster_id", insertable = false, updatable = false)
    private User poster;

    @OneToMany(mappedBy = "answer")
    private List<Reply> replies;

    public void addReply(Reply reply) {
        replies.add(reply);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", posterId=" + posterId +
                ", createDate=" + createDate +
                ", vote=" + vote +
                ", content='" + content + '\'' +
                ", userRankPoint='" + userRankPoint +'\'' +
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

    public void unMark() {
        rankPoint -= 5;
        mark--;
    }

    public void mark() {
        rankPoint += 5;
        mark++;
    }

    public void unLike() {
        rankPoint -= 1;
        like--;
    }

    public void like() {
        rankPoint += 1;
        like++;
    }

    public void vote() {
        rankPoint += 10;
        vote ++;
    }

    public void unVote() {
        rankPoint -= 10;
        vote --;
    }

    public void addUserRankPoint(int point) {
        userRankPoint += point;
    }
}
