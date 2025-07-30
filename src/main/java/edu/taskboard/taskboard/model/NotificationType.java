package edu.taskboard.taskboard.model;

public enum NotificationType {
    ACCOUNT_ACTIVATION,        // Lors de l’inscription : lien d’activation
    PASSWORD_RESET,            // Demande de réinitialisation du mot de passe
    INVITATION_TO_PROJECT,     // Invitation à rejoindre un projet
    TASK_ASSIGNED,             // Une tâche est assignée à l’utilisateur
    TASK_UPDATED,              // Une tâche à laquelle il participe a changé
    TASK_COMPLETED,            // Une tâche est marquée comme terminée
    COMMENT_MENTION,           // Mention de l’utilisateur dans un commentaire
    DEADLINE_REMINDER,         // Rappel d’échéance proche
    ACCOUNT_DELETION_WARNING,  // Avertissement avant suppression de compte
    NEW_MESSAGE,               // Nouveau message ou notification privée
    GENERAL_ANNOUNCEMENT       // Communication générale (nouveautés, maintenance, etc.)
}
