package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String filename;

    @Column
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Profile uploadedBy;

    @Column
    private String googleFileId;
}
