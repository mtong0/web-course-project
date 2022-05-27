package com.example.backend.controller.dto;

import com.example.backend.entity.Reply;
import lombok.Data;

import java.util.Date;

@Data
public class ReplyDTO {
    private int posterID;
    private String posterName;
    private String content;
    private Date createDate;
    private int like;
    private int answerId;
    public ReplyDTO(Reply reply) {
        posterID = reply.getPosterId();
        posterName = reply.getPoster().getNickName();
        content = reply.getContent();
        createDate = reply.getCreateDate();
        like = reply.getLike();
        answerId = reply.getAnswerId();
    }
}
