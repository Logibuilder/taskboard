package edu.taskboard.taskboard.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "validations")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant creation;
    private Instant expire;
    private Instant activation;
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "validator")
    private User user;
}
