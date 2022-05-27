package com.example.backend.service;

import com.example.backend.entity.Reply;
import com.example.backend.repo.ReplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {
    @Autowired
    ReplyRepo replyRepo;

    @Transactional
    public void postReply(Reply reply) {
        replyRepo.save(reply);
    }
}
