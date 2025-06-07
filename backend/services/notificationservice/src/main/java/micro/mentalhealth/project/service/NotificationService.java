package micro.mentalhealth.project.service;

import micro.mentalhealth.project.dto.NotificationDTO;
import micro.mentalhealth.project.mapper.NotificationMapper;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public NotificationService(NotificationRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Notification createNotification(NotificationDTO dto) {
        Notification notification = NotificationMapper.toEntity(dto);
        Notification saved = repository.save(notification);
        rabbitTemplate.convertAndSend("notification.exchange", "notification.route", saved);
        return saved;
    }

    public List<Notification> getNotificationsByDestinataire(String destinataireId) {
        return repository.findByDestinataireId(UUID.fromString(destinataireId));
    }
}
