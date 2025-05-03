package edu.taskboard.taskboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@IdClass(MemberId.class)
@Table(name = "members")
public class Member {
    @Id
    @ManyToOne
    @JoinColumn(name = "member", referencedColumnName = "id")
    private User member;

    @Id
    @ManyToOne
    @JoinColumn(name = "project", referencedColumnName = "id")
    private Project project;
}

