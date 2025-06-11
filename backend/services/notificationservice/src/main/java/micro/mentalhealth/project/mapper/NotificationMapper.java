package micro.mentalhealth.project.mapper;

import micro.mentalhealth.project.dto.NotificationDto;
import micro.mentalhealth.project.model.Notification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationDto.builder()
                .id(notification.getId() != null ? UUID.fromString(notification.getId()) : null)
                .destinataireId(notification.getDestinataireId())
                .message(notification.getMessage())
                .dateEnvoi(notification.getDateEnvoi())
                .statut(notification.getStatut())
                .build();
    }


    public Notification toEntity(NotificationDto dto) {
        if (dto == null) {
            return null;
        }

        return Notification.builder()
                .id(dto.getId() != null ? dto.getId().toString() : null)
                .destinataireId(dto.getDestinataireId())
                .message(dto.getMessage())
                .dateEnvoi(dto.getDateEnvoi())
                .statut(dto.getStatut())
                .build();
    }
}
