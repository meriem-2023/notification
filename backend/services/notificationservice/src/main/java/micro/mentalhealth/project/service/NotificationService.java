package micro.mentalhealth.project.service;

import lombok.RequiredArgsConstructor;
import micro.mentalhealth.project.dto.*;
import micro.mentalhealth.project.mapper.NotificationMapper;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.model.NotificationStatut;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
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

    @Transactional
    public NotificationDto createManualNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .destinataireId(request.getDestinataireId())
                .message(request.getMessage())
                .dateEnvoi(LocalDateTime.now())
                .statut(NotificationStatut.NON_LUE)
                .build();

        Notification saved = notificationRepository.save(notification);
        log.info("Notification manuelle créée : {}", saved);
        return notificationMapper.toDto(saved);
    }

    @Transactional
    public NotificationDto updateNotificationStatus(UUID id, NotificationStatut newStatus) {
        return notificationRepository.findById(id.toString())
                .map(notification -> {
                    notification.setStatut(newStatus);
                    log.info("Mise à jour du statut de la notification {} : {}", id, newStatus);
                    return notificationMapper.toDto(notificationRepository.save(notification));
                })
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
    }

    @Transactional
    public NotificationDto resendNotification(UUID id) {
        return notificationRepository.findById(id.toString())
                .map(notification -> {
                    notification.setDateEnvoi(LocalDateTime.now());
                    notification.setStatut(NotificationStatut.ENVOYEE);
                    log.info("Renvoi de la notification {}", id);
                    return notificationMapper.toDto(notificationRepository.save(notification));
                })
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
    }

    @Transactional
    public void handleDeliveryStatusUpdate(UUID notificationId, boolean success, String details) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId.toString());
        optionalNotification.ifPresent(notification -> {
            notification.setStatut(success ? NotificationStatut.ENVOYEE : NotificationStatut.ECHEC);
            notificationRepository.save(notification);
            log.info("Mise à jour du statut de livraison : {} - {}", success ? "Succès" : "Échec", details);
        });
    }

    @Transactional
    public void handleReadStatusUpdate(UUID notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId.toString());
        optionalNotification.ifPresent(notification -> {
            notification.setStatut(NotificationStatut.LUE);
            notificationRepository.save(notification);
            log.info("Notification {} marquée comme lue.", notificationId);
        });
    }

    public void handleAppointmentBookedEvent(AppointmentBookedEvent event) {
        saveNotificationWithRetry(event.getPatientId(), "Votre rendez-vous est réservé pour le " + event.getDate());
    }

    public void handleAppointmentConfirmedEvent(AppointmentConfirmedEvent event) {
        saveNotificationWithRetry(event.getPatientId(), "Votre rendez-vous du " + event.getDate() + " a été confirmé.");
    }

    public void handleAppointmentCancelledEvent(AppointmentCancelledEvent event) {
        saveNotificationWithRetry(event.getPatientId(), "Votre rendez-vous du " + event.getDate() + " a été annulé. Motif : " + event.getReason());
    }

    public void handleProgramAppointmentsCancelledEvent(ProgramAppointmentsCancelledEvent event) {
        saveNotificationWithRetry(event.getPatientId(), "Tous les rendez-vous du programme ont été annulés. Motif : " + event.getReason());
    }

    public void handlePaymentReceivedEvent(PaymentReceivedEvent event) {
        saveNotificationWithRetry(event.getPayerId(), "Paiement de " + event.getAmount() + "€ reçu avec succès.");
    }

    public void handleFeedbackSubmittedEvent(FeedbackSubmittedEvent event) {
        saveNotificationWithRetry(event.getTherapistId(), "Nouveau feedback reçu : " + event.getComment());
    }

    private void saveNotificationWithRetry(UUID destinataireId, String message) {
        int retryCount = 0;
        while (retryCount < 3) {
            try {
                saveNotification(destinataireId, message);
                log.info("Notification enregistrée après {} tentative(s)", retryCount);
                return;
            } catch (Exception e) {
                log.warn("Échec de l'enregistrement, tentative {}", retryCount);
                retryCount++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }
        log.error("Échec définitif après plusieurs tentatives pour destinataire {}", destinataireId);
    }

    private void saveNotification(UUID destinataireId, String message) {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .destinataireId(destinataireId)
                .message(message)
                .dateEnvoi(LocalDateTime.now())
                .statut(NotificationStatut.NON_LUE)
                .build();

        notificationRepository.save(notification);
        log.info("Notification sauvegardée : {}", notification);
    }
}
