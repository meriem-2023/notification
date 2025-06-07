package micro.mentalhealth.project.repository;

import micro.mentalhealth.project.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, UUID> {
    List<Notification> findByDestinataireId(UUID destinataireId);
}

