package edu.taskboard.taskboard.service;

import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.model.Validation;
import edu.taskboard.taskboard.repository.ValidationRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@Transactional
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    public  ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
    }

    public void saveValidation(User user) {
        Validation validation = new Validation();
        Instant t = Instant.now();

        validation.setUser(user);

        validation.setCreation(t);

        validation.setExpire(t.plus(10, MINUTES));

        Random random = new Random();

        int randomInt = random.nextInt(999999);

        String code = String.format("%06d", randomInt);

        validation.setCode(code);

        validationRepository.save(validation);
        notificationService.sendMail(validation);
    }


    public Validation findByCode(String code) {
        return validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("No validation found for code: " + code));
    }
}
