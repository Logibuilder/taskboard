package edu.taskboard.taskboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "task_stages")
public class TaskStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;


    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    private Project project;

    @OneToMany(
            mappedBy = "taskStage",
            cascade = CascadeType.ALL,
            orphanRemoval = true, //
            fetch = FetchType.LAZY
    )
    private List<Task> tasks= new ArrayList<>();
}
