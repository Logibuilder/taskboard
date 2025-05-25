package edu.taskboard.taskboard.controller;

import edu.taskboard.taskboard.exception.business.DuplicateResourceException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.modelDto.AuthenticationDTO;
import edu.taskboard.taskboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class userController {

    private final UserService userService;



    public userController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        log.info("Tentative d'inscription pour l'email: {}", user.getEmail());

        if (userService.existsByEmail(user.getEmail())) {
            log.warn("Email déjà utilisé: {}", user.getEmail());
            throw new DuplicateResourceException("Email", user.getEmail());
        }

        userService.signup(user);
        log.info("Inscription réussie pour l'email: {}", user.getEmail());
        System.out.println("ooooooooooooooooooooooooooooo");
        return ResponseEntity.ok("Utilisateur enregistré avec succès");

    }




    @PostMapping("/connect")
    public Map<String, String> connect(@RequestBody AuthenticationDTO authenticationDTO) {

        log.info("____________________________resultat {} , ____________________________________________________");
        return null;
    }




    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Récupération de tous les utilisateurs");
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }


}