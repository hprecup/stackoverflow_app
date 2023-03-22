package com.stackoverflow.project.Stackoverflow.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "f_name") // trebuie pus nullable??
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "score")
    private Long score;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Question> questions;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Answer> answers;
}
