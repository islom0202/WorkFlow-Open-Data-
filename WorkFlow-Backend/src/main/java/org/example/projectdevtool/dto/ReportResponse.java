package org.example.projectdevtool.dto;

import lombok.Data;
import org.example.projectdevtool.entity.Project;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReportResponse {
    private Long id;
    private String projectName;
    private String projectDesc;
    private double completedRate;  //Represents the percentage of the project that has been completed relative to the total scope of work.
    private String teamPerformance;
    private String spentTime;
    private LocalDate startAt;
    private LocalDate endAt;
    private List<Long> tasksCompleted; // IDs of tasks completed.
    private List<Long> totalTasks;  // IDs of total tasks.
    private int teamSize;
    private List<Long> employees;  // IDs of total employees.
    private double budgetUsed;
    private double totalBudget;
    private double qualityScore;  //task rate larini hisobini chiqarish
    private String summary;

    public ReportResponse(Long id, String projectName, String projectDesc, double completedRate, String teamPerformance, String spentTime, LocalDate startAt, LocalDate endAt, List<Long> tasksCompleted, List<Long> totalTasks,int teamSize, List<Long> employees, double budgetUsed, double totalBudget, double qualityScore, String summary) {
        this.id = id;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.completedRate = completedRate;
        this.teamPerformance = teamPerformance;
        this.spentTime = spentTime;
        this.startAt = startAt;
        this.endAt = endAt;
        this.tasksCompleted = tasksCompleted;
        this.totalTasks = totalTasks;
        this.teamSize = teamSize;
        this.employees = employees;
        this.budgetUsed = budgetUsed;
        this.totalBudget = totalBudget;
        this.qualityScore = qualityScore;
        this.summary = summary;
    }
}
