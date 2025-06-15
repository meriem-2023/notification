package micro.mentalhealth.project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    private UUID destinataireId;

    private String message;

    private LocalDateTime dateEnvoi;

    private NotificationStatut statut;

    // Le getter est déjà généré par Lombok @Data, mais si tu veux l’écrire explicitement :
    private RecipientType recipientType;  // Ajout du champ

}
