package org.example.projectdevtool.dto;

import lombok.Data;

@Data
public class ProjectResponse {
    private Long id;
    private String projectName;
    private String ownerName;
}
