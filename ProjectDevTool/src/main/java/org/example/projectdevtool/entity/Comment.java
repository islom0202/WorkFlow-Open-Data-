package org.example.projectdevtool.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn
    private Profile sender;

    @ManyToOne
    @JoinColumn
    private Task task;

    @Column
    private LocalDateTime sentAt;

    @Column
    private boolean isRead;
}
