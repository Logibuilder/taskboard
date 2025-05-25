package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;








    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.error("DEBUG - Nombre d'utilisateurs chargés : {}", users.size()); // Log en ERROR pour être bien visible
        users.forEach(u -> log.debug("User loaded: ID={}, Email={}", u.getId(), u.getEmail()));
        return users;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void signup(User user) {

        this.save(user);

        validationService.saveValidation(user);
    }


    private Optional<User> findByEmail(String email) {
         return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return this.findByEmail(email).isPresent();
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
