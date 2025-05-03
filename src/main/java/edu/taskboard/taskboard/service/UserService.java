package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void signup(User user) {
        String passwordCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypt);
        userRepository.save(user);
    }


    private User findByMail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.orElse(null);
    }

    public boolean existsByEmail(String email) {
        return findByMail(email) != null;
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public User update(Long id , User newUser) {

        if (id == null || newUser == null) {
            throw new IllegalArgumentException("id or newUser can not be null");
        }

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found with id: " + id));

        // Mettre à jour uniquement les champs nécessaires
        userToUpdate.setName(newUser.getName());
        userToUpdate.setEmail(newUser.getEmail());
        userToUpdate.setColor(newUser.getColor());
        userToUpdate.setRole(newUser.getRole());

        return userToUpdate;
    }
}
