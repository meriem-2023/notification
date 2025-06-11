package micro.mentalhealth.project.repository;

import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.model.NotificationStatut;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByDestinataireIdOrderByDateEnvoiDesc(UUID destinataireId);

    List<Notification> findByStatut(NotificationStatut statut);

    List<Notification> findByDestinataireId(UUID uuid);

    // Corrigé ici, retourne une liste de Notification et non Collection<Object>
    List<Notification> findByDestinataireIdAndStatut(UUID destinataireId, NotificationStatut statut);

}
