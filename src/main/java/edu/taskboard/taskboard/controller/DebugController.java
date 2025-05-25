package edu.taskboard.taskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api/conn")
public class DebugController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public ResponseEntity<String> checkDb() {
        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM user");
            rs.next();
            return ResponseEntity.ok("Connexion OK - Nombre d'utilisateurs: " + rs.getInt(1));
        } catch (SQLException e) {
            return ResponseEntity.status(500)
                    .body("Erreur DB: " + e.getMessage());
        }
    }
}