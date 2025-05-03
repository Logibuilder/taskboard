package edu.taskboard.taskboard.repository;

import edu.taskboard.taskboard.model.TaskStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStageRepository extends JpaRepository<TaskStage, Long> {
}
