package edu.taskboard.taskboard.controller;

import edu.taskboard.taskboard.exception.business.DuplicateResourceException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping(value = "api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void  signup(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email", user.getEmail());
        }
        log.info("signup............");
        userService.signup(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user not found with id: " + id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "User not found with id: " + id);
        }

    }
}