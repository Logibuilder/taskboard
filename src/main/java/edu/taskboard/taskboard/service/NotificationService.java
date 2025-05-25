package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.model.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    JavaMailSender mailSender;


    public void sendMail(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@taskboard.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation");

        String text = String.format(
                "Bonjour %s <br />, Votre code d'activation est %s. <br />A bientôt !!!",
                validation.getUser().getName(),
                validation.getCode());

        message.setText(text);
        mailSender.send(message);
    }


}
