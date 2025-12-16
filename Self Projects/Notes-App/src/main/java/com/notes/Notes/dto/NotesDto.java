package com.notes.Notes.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotesDto {


    private Long id;
    private String title;
    private  String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favourite;

}
