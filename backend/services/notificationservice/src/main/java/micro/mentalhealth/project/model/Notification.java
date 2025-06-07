
package micro.mentalhealth.project.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import micro.mentalhealth.project.model.enums.Statut;

@Document(collection = "notifications")
public class Notification {

    @Id
    private UUID id;
    private UUID destinataireId;
    private String message;
    private LocalDateTime dateEnvoi;
    private Statut statut;

    public Notification() {
        this.id = UUID.randomUUID();
        this.dateEnvoi = LocalDateTime.now();
        this.statut = Statut.ENVOYE;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getDestinataireId() {
        return destinataireId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public Statut getStatut() {
        return statut;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setDestinataireId(UUID destinataireId) {
        this.destinataireId = destinataireId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
