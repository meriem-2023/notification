package micro.mentalhealth.project.mapper;

import micro.mentalhealth.project.dto.NotificationDTO;
import micro.mentalhealth.project.model.Notification;

import java.util.UUID;

public class NotificationMapper {

    public static Notification toEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setDestinataireId(UUID.fromString(dto.getDestinataireId()));
        notification.setMessage(dto.getMessage());
        return notification;
    }
}
