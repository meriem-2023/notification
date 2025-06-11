package micro.mentalhealth.project.service;

import micro.mentalhealth.project.dto.CreateNotificationRequest;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.model.NotificationStatut;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationListener {

    private final NotificationRepository repository;

    public NotificationListener(NotificationRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "notification.queue")
    public void handleMessage(CreateNotificationRequest event) {
        Notification notif = new Notification();
        notif.setId(String.valueOf(UUID.randomUUID()));
        notif.setDestinataireId(UUID.fromString(event.getUserId()));
        notif.setMessage(event.getMessage());
        notif.setDateEnvoi(LocalDateTime.now());
        notif.setStatut(NotificationStatut.ENVOYEE);
        repository.save(notif);
    }
}
