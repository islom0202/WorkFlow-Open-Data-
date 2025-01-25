package org.example.projectdevtool.dto;

import lombok.Data;
import org.example.projectdevtool.entity.Status;

@Data
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private String projectName;
    private String assignedTo;
    private Status status;
    private String prior;
    private Long projectOwnerId;
    private int rating;

    public TaskResponse(Long id, String name, String description, String projectName, String assignedTo, Status status, String prior, Long projectOwnerId, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectName = projectName;
        this.assignedTo = assignedTo;
        this.status = status;
        this.prior = prior;
        this.projectOwnerId = projectOwnerId;
        this.rating = rating;
    }
}
