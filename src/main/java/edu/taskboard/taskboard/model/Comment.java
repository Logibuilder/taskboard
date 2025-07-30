package edu.taskboard.taskboard.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long  id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task")
    private Task task;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime date;
}
