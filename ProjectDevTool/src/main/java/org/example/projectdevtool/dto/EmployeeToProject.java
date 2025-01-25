package org.example.projectdevtool.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeToProject {
    private Long projectId;
    private Long ownerId;
    private List<Long> employeesId;
}
