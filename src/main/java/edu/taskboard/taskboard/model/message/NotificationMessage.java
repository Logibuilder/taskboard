package edu.taskboard.taskboard.model.message;

public interface NotificationMessage {
    String getSubject();
    String getHtmlContent();
    String getPlainTextContent();
}