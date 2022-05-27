package com.example.backend.repo;

import com.example.backend.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepo extends JpaRepository<Reply, Integer> {
}
