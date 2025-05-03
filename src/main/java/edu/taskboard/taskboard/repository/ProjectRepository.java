package edu.taskboard.taskboard.repository;

import edu.taskboard.taskboard.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository  extends JpaRepository<Project, Long> {
}
