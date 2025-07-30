package edu.taskboard.taskboard.model.message;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class AccountActivation implements NotificationMessage{

    private final String name;
    private final String activationCode;

    public AccountActivation(String name, String activationCode) {
        this.name = name;
        this.activationCode = activationCode;
    }

    @Override
    public String getSubject() {
        return "Activez votre compte, " + name;
    }

    @Override
    public String getHtmlContent() {
        return """
            <html>
              <body>
                <h2>Bonjour %s,</h2>
                <p>Merci de vous être inscrit.</p>
                <p>Voici votre code d’activation :</p>
                <h3 style="color:#1a73e8;">%s</h3>
                <p>Copiez ce code dans l’application pour activer votre compte.</p>
                <br>
                <img src="cid:logo" alt="Logo" style="width:100px;" />
              </body>
            </html>
            """.formatted(name, activationCode);
    }

    @Override
    public String getPlainTextContent() {
        return """
            Bonjour %s,

            Merci de vous être inscrit.
            Votre code d’activation est : %s

            Copiez ce code dans l’application pour activer votre compte.
            """.formatted(name, activationCode);
    }
}
