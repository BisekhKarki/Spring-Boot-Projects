package com.bisekh.Task.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TaskDto {

    private String id;

    private String title;
    private String description;
    private boolean status;
    private LocalDateTime createdAt;
}
