package org.example.projectdevtool.dto;

import lombok.Data;
import org.example.projectdevtool.entity.Project;

import java.time.LocalDate;

@Data
public class ProjectRequestDto {
    private Long ownerId;
    private String name;
    private String description; // Renamed from 'desc'
    private double budget;
    private LocalDate startDate;
    private LocalDate endDate;
}
