package edu.taskboard.taskboard.repository;

import edu.taskboard.taskboard.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByCode(String activationCode);
}
