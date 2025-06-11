package micro.mentalhealth.project.dto;

import lombok.Builder;
import lombok.Data;
import micro.mentalhealth.project.model.NotificationStatut;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationDto {
    private UUID id;
    private UUID destinataireId;
    private String message;
    private LocalDateTime dateEnvoi;
    private NotificationStatut statut;
}
