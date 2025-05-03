package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.repository.TaskStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStageService {

    @Autowired
    private TaskStageRepository taskStageRepository;
}
