package com.notes.Notes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private  String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favourite;
}
