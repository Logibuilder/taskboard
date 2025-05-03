package edu.taskboard.taskboard.repository;

import edu.taskboard.taskboard.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
