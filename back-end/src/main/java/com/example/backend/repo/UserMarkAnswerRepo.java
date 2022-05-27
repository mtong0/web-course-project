package com.example.backend.repo;

import com.example.backend.entity.UserMarkAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMarkAnswerRepo extends JpaRepository<UserMarkAnswer, Integer> {
    public UserMarkAnswer findByUserIdAndAnswerId(int userId, int answerId);
    public List<UserMarkAnswer> findAllByUserId(int userId);

}
