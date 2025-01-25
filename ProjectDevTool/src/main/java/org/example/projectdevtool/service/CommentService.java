package org.example.projectdevtool.service;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.CommentRequest;
import org.example.projectdevtool.dto.CommentResponse;
import org.example.projectdevtool.entity.Comment;
import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Project;
import org.example.projectdevtool.entity.Task;
import org.example.projectdevtool.repo.CommentRepo;
import org.example.projectdevtool.repo.ProfileRepo;
import org.example.projectdevtool.repo.ProjectRepo;
import org.example.projectdevtool.repo.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final ProfileRepo profileRepo;
    private final ProjectRepo projectRepo;
    private final TaskRepo taskRepo;

    public CommentResponse addComment(CommentRequest request) {

        Profile sender = profileRepo.findById(request.getSenderId())
                .orElseThrow(() -> new NoSuchElementException("not found"));

        Task task = taskRepo.findById(request.getTaskId())
                .orElseThrow(() -> new NoSuchElementException("not found"));

        Project project = projectRepo.findById(task.getProject().getId())
                .orElseThrow(() -> new NoSuchElementException("not found"));

        Comment comment = new Comment();

        if (project.getEmployees().contains(sender)) {
            comment.setComment(request.getComment());
            comment.setRead(false);
            comment.setSentAt(LocalDateTime.now());
            comment.setTask(task);
            comment.setSender(sender);
            commentRepo.save(comment);

            return new CommentResponse(
                    comment.getId(),
                    comment.getComment(),
                    comment.getSender().getUser().getEmail(),
                    comment.getTask().getName(),
                    comment.getSentAt()
            );
        } else
            throw new RuntimeException("permission denied");

    }
}
