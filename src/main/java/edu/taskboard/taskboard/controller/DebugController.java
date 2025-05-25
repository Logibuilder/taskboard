package edu.taskboard.taskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-connection")
    public ResponseEntity<String> testDbConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return ResponseEntity.ok("Connexion DB OK - URL: " + connection.getMetaData().getURL());
        }
    }
}