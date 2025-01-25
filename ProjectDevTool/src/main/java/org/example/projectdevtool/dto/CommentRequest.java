package org.example.projectdevtool.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String comment;
    private Long senderId; // userId
    private Long taskId;
}
