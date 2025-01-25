package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @Column
    private double completedRate;  //Represents the percentage of the project that has been completed relative to the total scope of work.

    @Column
    private String teamPerformance;

    @Column
    private String spentTime;

    @JoinColumn
    private LocalDate startAt;

    @JoinColumn
    private LocalDate endAt;

    @ElementCollection  // For storing a list of basic types in a separate table
    @CollectionTable(name = "tasks_completed", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "task_id")
    private List<Long> tasksCompleted; // IDs of tasks completed.

    @ElementCollection
    @CollectionTable(name = "total_tasks", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "task_id")
    private List<Long> totalTasks;  // IDs of total tasks.

    @JoinColumn
    private int teamSize;

    @ElementCollection
    @CollectionTable(name = "employees", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "employees")
    private List<Long> employees;  // IDs of total employees.

    @JoinColumn
    private double budgetUsed;

    @JoinColumn
    private double totalBudget;

    @JoinColumn
    private double qualityScore;  //task rate larini hisobini chiqarish

    @JoinColumn
    private String summary;
}
