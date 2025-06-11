package micro.mentalhealth.project.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import micro.mentalhealth.project.service.NotificationService;
import micro.mentalhealth.project.dto.NotificationDto;
import micro.mentalhealth.project.dto.CreateNotificationRequest;
import micro.mentalhealth.project.dto.NotificationStatusUpdate;
import micro.mentalhealth.project.dto.DeliveryCallbackRequest; // Pour les webhooks
import micro.mentalhealth.project.dto.ReadCallbackRequest;   // Pour les webhooks

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @api {get} /api/notifications/{destinataireId} Récupérer les notifications par destinataire
     * @apiGroup Notifications
     * @apiParam {UUID} destinataireId L'ID du patient ou du thérapeute.
     * @apiSuccess {NotificationDto[]} notifications Liste des notifications.
     */
    @GetMapping("/{destinataireId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByDestinataireId(@PathVariable UUID destinataireId) {
        List<NotificationDto> notifications = notificationService.getNotificationsByDestinataireId(destinataireId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * @api {get} /api/notifications/{destinataireId}/unread Récupérer les notifications non lues
     * @apiGroup Notifications
     * @apiParam {UUID} destinataireId L'ID du patient ou du thérapeute.
     * @apiSuccess {NotificationDto[]} notifications Liste des notifications non lues.
     */
    @GetMapping("/{destinataireId}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotificationsByDestinataireId(@PathVariable UUID destinataireId) {
        List<NotificationDto> notifications = notificationService.getUnreadNotificationsByDestinataireId(destinataireId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * @api {post} /api/notifications/send-manual Créer et envoyer manuellement une notification
     * @apiGroup Notifications
     * @apiBody {CreateNotificationRequest} request Détails de la notification.
     * @apiSuccess {NotificationDto} notification La notification créée.
     */
    @PostMapping("/send-manual")
    public ResponseEntity<NotificationDto> createManualNotification(@RequestBody CreateNotificationRequest request) {
        NotificationDto createdNotification = notificationService.createManualNotification(request);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    /**
     * @api {put} /api/notifications/{id}/status Mettre à jour le statut d'une notification
     * @apiGroup Notifications
     * @apiParam {UUID} id L'ID de la notification.
     * @apiBody {NotificationStatusUpdate} statusUpdate Le nouveau statut.
     * @apiSuccess {NotificationDto} notification La notification mise à jour.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationDto> updateNotificationStatus(@PathVariable UUID id, @RequestBody NotificationStatusUpdate statusUpdate) {
        NotificationDto updatedNotification = notificationService.updateNotificationStatus(id, statusUpdate.getNewStatus());
        return ResponseEntity.ok(updatedNotification);
    }

    /**
     * @api {post} /api/notifications/{id}/resend Retenter l'envoi d'une notification
     * @apiGroup Notifications
     * @apiParam {UUID} id L'ID de la notification.
     * @apiSuccess {NotificationDto} notification La notification dont l'envoi est retenté.
     */
    @PostMapping("/{id}/resend")
    public ResponseEntity<NotificationDto> resendNotification(@PathVariable UUID id) {
        NotificationDto resendingNotification = notificationService.resendNotification(id);
        return ResponseEntity.ok(resendingNotification);
    }

    /**
     * @api {post} /api/notifications/callback/delivery Gérer les retours de livraison
     * @apiGroup Notifications
     * @apiBody {DeliveryCallbackRequest} request Informations sur la livraison.
     * @apiSuccess (200) OK
     * @apiDescription Cet endpoint serait appelé par les services d'email/SMS/push pour notifier du statut de livraison.
     */
    @PostMapping("/callback/delivery")
    public ResponseEntity<Void> handleDeliveryCallback(@RequestBody DeliveryCallbackRequest request) {
        notificationService.handleDeliveryStatusUpdate(request.getNotificationId(), request.isSuccess(), request.getDetails());
        return ResponseEntity.ok().build();
    }

    /**
     * @api {post} /api/notifications/callback/read Gérer les retours de lecture
     * @apiGroup Notifications
     * @apiBody {ReadCallbackRequest} request Informations sur la lecture.
     * @apiSuccess (200) OK
     * @apiDescription Cet endpoint serait appelé par les clients mobile/web ou services pour notifier de la lecture.
     */
    @PostMapping("/callback/read")
    public ResponseEntity<Void> handleReadCallback(@RequestBody ReadCallbackRequest request) {
        notificationService.handleReadStatusUpdate(request.getNotificationId());
        return ResponseEntity.ok().build();
    }
}

