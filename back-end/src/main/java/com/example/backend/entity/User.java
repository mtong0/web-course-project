package com.example.backend.entity;

import com.example.backend.controller.dto.MarkedAnswer;
import com.example.backend.controller.dto.QuestionCard;
import com.example.backend.repo.AnswerRepo;
import com.example.backend.repo.QuestionRepo;
import com.example.backend.repo.UserMarkAnswerRepo;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Transient
    QuestionRepo questionRepo;
    @Transient
    AnswerRepo answerRepo;
    @Transient
    UserMarkAnswerRepo userMarkAnswerRepo;

    @Id
    private int id;
    private String nickName;
    private String email;
    private String password;
    private int experience;
    private String topics;
    private String vector;

    @OneToMany(mappedBy = "poster")
    private List<Question> questions;

    @OneToMany(mappedBy = "poster")
    private List<Answer> answers;

    @ManyToMany
    @JoinTable(
            name = "user_vote_answer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    List<Answer> votedAnswer;

    @ManyToMany
    @JoinTable(
            name = "user_follow_question",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    List<Question> followedQuestions;

    @ManyToMany
    @JoinTable(
            name = "user_mark_answer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    List<Answer> markedAnswer;

    @ManyToMany
    @JoinTable(
            name = "user_like_answer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    List<Answer> likeAnswers;

    @ManyToMany
    @JoinTable(
            name = "user_vote_question",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    List<Question> votedQuestions;
    public boolean voteQuestion(int questionId) {
        System.out.println("vote"+questionId);
        System.out.println(votedQuestions);
        for (Question question: votedQuestions) {
            if (question.getId() == questionId) {
                question.unVote();
                votedQuestions.remove(question);
                return false;
            }
        }
        Question question = questionRepo.findById(questionId).orElse(null);
        assert question != null;
        question.vote();
        votedQuestions.add(question);
        return true;
    }

    public boolean voteForAnswer(int answerId) {
        for (Answer answer: votedAnswer) {
            if (answer.getId() == answerId) {
                answer.unVote();
                answerRepo.saveAndFlush(answer);
                votedAnswer.remove(answer);
                return false;
            }
        }
        Answer answer = answerRepo.findById(answerId).orElse(null);
        assert answer != null;
        answer.vote();
        votedAnswer.add(answer);
        return true;
    }

    public boolean markAnswer(int answerId) {
        for (Answer answer: markedAnswer) {
            if (answer.getId() == answerId) {
                answer.unMark();
                answerRepo.saveAndFlush(answer);
                markedAnswer.remove(answer);
                return false;
            }
        }
        Answer answer = answerRepo.findById(answerId).orElse(null);
        assert answer != null;
        answer.mark();
        markedAnswer.add(answer);
        addExperience(5);
        return true;
    }

    public boolean likeAnswer(int answerId) {
        for (Answer answer: likeAnswers) {
            if (answer.getId() == answerId) {
                answer.unLike();
                answerRepo.saveAndFlush(answer);
                markedAnswer.remove(answer);
                return false;
            }
        }
        Answer answer = answerRepo.findById(answerId).orElse(null);
        assert answer != null;
        answer.like();
        likeAnswers.add(answer);
        addExperience(5);
        return true;
    }

    public void addExperience(int exp) {
        this.experience += exp;
    }

    public List<MarkedAnswer> getMarkedAnswer(){
        List<MarkedAnswer> answers = new ArrayList<>();
        for (Answer answer: markedAnswer) {
            UserMarkAnswer userMarkAnswer = userMarkAnswerRepo.findByUserIdAndAnswerId(id, answer.getId());
            answers.add(new MarkedAnswer(answer, userMarkAnswer));
        }
        return answers;
    }

    public Badge getBadge(){
        if (experience < 300)
            return new Badge(300-experience, "None");
        else if (experience < 600)
            return new Badge(600-experience, "Iron");
        else if (experience < 1500)
            return new Badge(1500-experience, "Copper");
        else if (experience < 2500)
            return new Badge(2500-experience, "Silver");
        else
            return new Badge(5000-experience, "Gold");

    }
}
