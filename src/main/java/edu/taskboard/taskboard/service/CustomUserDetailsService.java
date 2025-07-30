package edu.taskboard.taskboard.service;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.business.ResourceNotFoundException;
import edu.taskboard.taskboard.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user", username));
    }


}
