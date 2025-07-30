package edu.taskboard.taskboard.service;

import edu.taskboard.taskboard.dto.Constants;
import edu.taskboard.taskboard.exception.business.CodeExpiredException;
import edu.taskboard.taskboard.exception.business.CodeFormatException;
import edu.taskboard.taskboard.exception.business.CodeInvalidException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.model.Validation;
import edu.taskboard.taskboard.model.message.AccountActivation;
import edu.taskboard.taskboard.repository.ValidationRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ValidationService {


    private final ValidationRepository validationRepository;

    private final NotificationService notificationService;






    public void saveValidation(User user) {
        Validation validation = new Validation();
        Instant t = Instant.now();

        validation.setUser(user);

        validation.setCreation(t);

        validation.setExpire(t.plus(Constants.VALIDATION_EXPIRATION_DELAY));

        Random random = new Random();

        int randomInt = random.nextInt(999999);

        String code = String.format("%06d", randomInt);

        validation.setCode(code);

        validationRepository.save(validation);
        notificationService.sendNotification(user.getUsername(), new AccountActivation(user.getName(), code));
    }


    public User activate(String code) {
        Validation validation = validationRepository.findByCode(code)
                .orElseThrow(() -> new CodeInvalidException());

        if (validation.isExpired()) {
            throw new CodeExpiredException();
        }

        User user =  validation.getUser();
        log.info("User {} activated ? ", user.isActive());
        user.setActive(true);
        log.info("After setActive, User {} activated ? ", user.isActive());


        return user;
    }
}
