package micro.mentalhealth.project.service;

import micro.mentalhealth.project.dto.CreateNotificationRequest;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.model.NotificationStatut;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationRepository repository;

    public NotificationListener(NotificationRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "notification.queue")
    public void handleMessage(CreateNotificationRequest event) {
        try {
            if (event.getUserId() == null || event.getMessage() == null) {
                log.warn("Notification avec données invalides: {}", event);
                return;
            }

            Notification notif = new Notification();
            notif.setId(UUID.randomUUID().toString());
            notif.setDestinataireId(UUID.fromString(event.getUserId()));
            notif.setMessage(event.getMessage());
            notif.setDateEnvoi(LocalDateTime.now());
            notif.setStatut(NotificationStatut.ENVOYEE);

            repository.save(notif);
            log.info("Notification sauvegardée avec succès: {}", notif);
        } catch (Exception e) {
            log.error("Erreur dans RabbitMQ Listener: {}", e.getMessage(), e);
        }
    }
}
