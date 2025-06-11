package micro.mentalhealth.project.dto;

import java.util.UUID;
import micro.mentalhealth.project.model.RecipientType;

public class CreateNotificationRequest {
    private UUID destinataireId;
    private RecipientType recipientType;
    private String message;

    // Getters et Setters
    public UUID getDestinataireId() { return destinataireId; }
    public void setDestinataireId(UUID destinataireId) { this.destinataireId = destinataireId; }
    public RecipientType getRecipientType() { return recipientType; }
    public void setRecipientType(RecipientType recipientType) { this.recipientType = recipientType; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUserId() {
        return destinataireId != null ? destinataireId.toString() : null;
    }
}
