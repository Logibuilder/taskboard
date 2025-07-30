package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.dto.LoginResponseDto;
import edu.taskboard.taskboard.dto.UserDTO;
import edu.taskboard.taskboard.exception.business.ResourceNotFoundException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.model.message.AccountActivation;
import edu.taskboard.taskboard.repository.UserRepository;
import edu.taskboard.taskboard.repository.ValidationRepository;
import edu.taskboard.taskboard.security.JwtService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       ValidationService validationService, AuthenticationManager authenticationManager, JwtService jwtService, AuthenticationManager authenticationManager1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager1;
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::fromEntity).toList();
    }


    public User getUserByUserName(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("username", username));
    }

    public Optional<User> findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }


    public boolean exist(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        log.info("Password encoded apres setPassword: {}", user.getPassword());
        userRepository.save(user);
        log.info("Password après sauveagrde: {}", userRepository.findByEmail(user.getEmail()).get().getPassword());

        validationService.saveValidation(user);

        log.info("User {} created", user.getUsername());
    }

    public void activate(String code) {
        User user  = validationService.activate(code);
        userRepository.save(user);
    }

    public LoginResponseDto loginUSer(String username, String password) {

        //Auth
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));


        User user = getUserByUserName(username);

        //generer les token
        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        //loginResponseDto

        UserDTO userDTO = UserDTO.fromEntity(user);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUser(userDTO);
        loginResponseDto.setAccessToken(accessToken);
        loginResponseDto.setRefreshToken(refreshToken);

        return loginResponseDto;
    }


}
