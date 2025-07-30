package edu.taskboard.taskboard.service;

import edu.taskboard.taskboard.model.message.NotificationMessage;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class NotificationService {

    @Autowired
    JavaMailSender mailSender;

    public void sendNotification(String userEmail, NotificationMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("assane.kanemb@gmail.com");
            helper.setTo(userEmail);
            helper.setSubject(message.getSubject());
            helper.setText(message.getPlainTextContent(), message.getHtmlContent()); // texte + html

            mailSender.send(mimeMessage);
            log.info("Email envoyé avec succès à {}", userEmail);

        } catch (MailException | MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email à {} : {}", userEmail, e.getMessage());

        }
    }


}
