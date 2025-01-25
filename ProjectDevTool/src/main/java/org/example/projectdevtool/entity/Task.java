package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    private Profile assignedTo;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private int score;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    private Prior prior;

    @JoinColumn
    private LocalDateTime assignedAt;

    public enum Prior{
        HIGH,
        MEDIUM,
        LOW
    }
}
