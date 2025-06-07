package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;

import micro.mentalhealth.project.model.enums.Statut;

public class NotificationDTO {
    private String id;
    private String destinataireId;
    private String message;
    private LocalDateTime dateEnvoi;
    private Statut statut;

    public NotificationDTO() {
    }

    public NotificationDTO(String id, String destinataireId, String message, LocalDateTime dateEnvoi, Statut statut) {
        this.id = id;
        this.destinataireId = destinataireId;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(String destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
