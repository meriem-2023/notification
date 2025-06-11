package micro.mentalhealth.project.service;

import lombok.RequiredArgsConstructor;
import micro.mentalhealth.project.dto.*;
import micro.mentalhealth.project.mapper.NotificationMapper;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.model.NotificationStatut;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationDto> getNotificationsByDestinataireId(UUID destinataireId) {
        return notificationRepository.findByDestinataireId(destinataireId)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationDto> getUnreadNotificationsByDestinataireId(UUID destinataireId) {
        return notificationRepository.findByDestinataireIdAndStatut(destinataireId, NotificationStatut.NON_LUE)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public NotificationDto createManualNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .destinataireId(request.getDestinataireId())
                .message(request.getMessage())
                .dateEnvoi(LocalDateTime.now())
                .statut(NotificationStatut.NON_LUE)
                .build();

        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDto(saved);
    }

    public NotificationDto updateNotificationStatus(UUID id, NotificationStatut newStatus) {
        Notification notification = notificationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        notification.setStatut(newStatus);
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    public NotificationDto resendNotification(UUID id) {
        Notification notification = notificationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        notification.setDateEnvoi(LocalDateTime.now());
        notification.setStatut(NotificationStatut.ENVOYEE);
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    public void handleDeliveryStatusUpdate(UUID notificationId, boolean success, String details) {
        Notification notification = notificationRepository.findById(String.valueOf(notificationId))
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        notification.setStatut(success ? NotificationStatut.ENVOYEE : NotificationStatut.ECHEC);
        notificationRepository.save(notification);
    }

    public void handleReadStatusUpdate(UUID notificationId) {
        Notification notification = notificationRepository.findById(String.valueOf(notificationId))
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        notification.setStatut(NotificationStatut.LUE);
        notificationRepository.save(notification);
    }

    public void handleAppointmentBookedEvent(AppointmentBookedEvent event) {
        String message = "Votre rendez-vous a été réservé avec succès pour le " + event.getDate();
        saveNotification(event.getPatientId(), message);
    }

    public void handleAppointmentConfirmedEvent(AppointmentConfirmedEvent event) {
        String message = "Votre rendez-vous du " + event.getDate() + " a été confirmé.";
        saveNotification(event.getPatientId(), message);
    }

    public void handleAppointmentCancelledEvent(AppointmentCancelledEvent event) {
        String message = "Votre rendez-vous du " + event.getDate() + " a été annulé. Motif : " + event.getReason();
        saveNotification(event.getPatientId(), message);
    }

    public void handleProgramAppointmentsCancelledEvent(ProgramAppointmentsCancelledEvent event) {
        String message = "Tous les rendez-vous du programme ont été annulés. Motif : " + event.getReason();
        saveNotification(event.getPatientId(), message);
    }

    public void handlePaymentReceivedEvent(PaymentReceivedEvent event) {
        String message = "Paiement de " + event.getAmount() + "€ reçu avec succès. Merci !";
        saveNotification(event.getPayerId(), message);
    }

    public void handleFeedbackSubmittedEvent(FeedbackSubmittedEvent event) {
        String message = "Nouveau feedback reçu de la part du patient : " + event.getComment();
        saveNotification(event.getTherapistId(), message);
    }

    private void saveNotification(UUID destinataireId, String message) {
        Notification notification = Notification.builder()
                .destinataireId(destinataireId)
                .message(message)
                .dateEnvoi(LocalDateTime.now())
                .statut(NotificationStatut.NON_LUE)
                .build();

        notificationRepository.save(notification);
    }
}
