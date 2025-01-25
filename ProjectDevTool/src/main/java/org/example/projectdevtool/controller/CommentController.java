package org.example.projectdevtool.controller;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.CommentRequest;
import org.example.projectdevtool.dto.CommentResponse;
import org.example.projectdevtool.repo.UsersRepo;
import org.example.projectdevtool.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UsersRepo usersRepo;


    @PostMapping("/add") //only employees in the same project can make a comment and read on a task of that task
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        CommentResponse response = commentService.addComment(request);

        String username = usersRepo.findById(request.getSenderId())
                .orElseThrow(()->new NoSuchElementException("not found")).getEmail();
        simpMessagingTemplate.convertAndSendToUser(username,"/topic/message", response);
        return ResponseEntity.ok("added");
    }
}
