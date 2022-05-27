package com.example.backend.entity;

import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.repo.AnswerRepo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Question {
    @Transient
    private AnswerRepo answerRepo;

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

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "poster_id", updatable = false, insertable = false)
    private User poster;

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public List<Answer> getAnswers(String type) {
        if (type.equals("NEW")) {
            List<Answer> res = answerRepo.findByQuestionIdOrderByCreateDateDesc(id);
        }
        List<Answer> res = answerRepo.findByQuestionIdOrderByVoteDesc(id);
        return res;
    }

    public QuestionCard getQuestionCard() {
        Answer answer = answerRepo.findFirstByQuestionIdAndPosterIdIsNotOrderByVoteDesc(id, 0);
        QuestionCard questionCard;
        if (answer!=null) {
            questionCard = new QuestionCard(
                    this, answer
            );
        } else {
            questionCard = new QuestionCard(
                    id,
                    name
            );
        }
        return questionCard;
    }

    public QuestionCard getQuestionCard(Answer answer) {
        return new QuestionCard(this, answer);
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
        this.modifiedDate = createDate;
    }

    public void vote() {
        vote++;
        rankPoint += 10;
    }

    public void unVote() {
        vote --;
        rankPoint -= 10;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", posterId=" + posterId +
                ", vote=" + vote +
                ", rankPoint=" + rankPoint +
                '}';
    }
}
