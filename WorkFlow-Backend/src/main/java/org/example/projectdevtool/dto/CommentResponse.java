package org.example.projectdevtool.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String comment;
    private String sender;
    private String task;
    private LocalDateTime sentAt;

    public CommentResponse(Long id, String comment, String sender, String task, LocalDateTime sentAt) {
        this.id = id;
        this.comment = comment;
        this.sender = sender;
        this.task = task;
        this.sentAt = sentAt;
    }
}
