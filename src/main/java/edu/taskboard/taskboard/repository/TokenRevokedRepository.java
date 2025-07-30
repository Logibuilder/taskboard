package edu.taskboard.taskboard.repository;

import edu.taskboard.taskboard.model.TokenRevoked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRevokedRepository extends JpaRepository<TokenRevoked, Long> {

    boolean existsByJti(String jti);
}
