package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description; // Renamed from 'desc'

    @Column
    private double budget;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate; //deadline

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Profile owner;

    @ManyToMany
    @JoinTable(
            name = "project_employees",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Profile> employees;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private int rate; // Completed rate, e.g., 80%
}
