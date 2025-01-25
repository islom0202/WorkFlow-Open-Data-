package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

//    @Column
//    @ManyToMany(mappedBy = "employees")
//    private List<Project> project;

    @Column
    private String profession;

    @Column
    private String goodAt;

    @OneToOne
    @JoinColumn
    private Users user;
}
