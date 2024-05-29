package com.example.demo.moduls;

import com.example.demo.moduls.enums.Product;
import com.example.demo.moduls.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "lectures")
@Data
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numberOfLectures")
    private int numberOfLectures;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    private LocalDateTime dateOfCreated;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private Course course;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

}