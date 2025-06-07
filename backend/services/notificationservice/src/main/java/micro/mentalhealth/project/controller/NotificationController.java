package micro.mentalhealth.project.controller;

import micro.mentalhealth.project.dto.NotificationDTO;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/add")
    public Notification addNotification(@RequestBody NotificationDTO dto) {
        return notificationService.createNotification(dto);
    }

    @GetMapping("/destinataire/{id}")
    public List<Notification> getByDestinataire(@PathVariable String id) {
        return notificationService.getNotificationsByDestinataire(id);
    }
}


