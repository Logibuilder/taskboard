package edu.taskboard.taskboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.  IDENTITY)
    private Long  id;

    private String title;

    private String description;


    private LocalDateTime deadline;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_stage")
    private TaskStage taskStage;
}
