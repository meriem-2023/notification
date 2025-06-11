package micro.mentalhealth.project.dto;
import micro.mentalhealth.project.model.NotificationStatut; // Assurez-vous d'importer le bon package

public class NotificationStatusUpdate {
    private NotificationStatut newStatus;

    // Getters et Setters
    public NotificationStatut getNewStatus() { return newStatus; }
    public void setNewStatus(NotificationStatut newStatus) { this.newStatus = newStatus; }
}