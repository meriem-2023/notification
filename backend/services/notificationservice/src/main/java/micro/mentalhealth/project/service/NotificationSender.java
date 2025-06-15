package micro.mentalhealth.project.service;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import micro.mentalhealth.project.model.Notification;
import micro.mentalhealth.project.dto.ContactInfo;
import micro.mentalhealth.project.model.RecipientType;
import java.util.UUID;

@Component
public class NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);
    private final UserServiceFeignClient userServiceFeignClient;
    private final NotificationService notificationService;

    public NotificationSender(UserServiceFeignClient userServiceFeignClient, NotificationService notificationService) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.notificationService = notificationService;
    }

    @Async
    public void sendNotification(Notification notification) {
        log.info("Tentative d'envoi de la notification {}: {}", notification.getId(), notification.getMessage());
        String deliveryDetails = "N/A";
        boolean success = false;

        try {
            ContactInfo contactInfo = userServiceFeignClient.getContactInfo(notification.getDestinataireId(), notification.getRecipientType());

            if (contactInfo.getEmail() != null && !contactInfo.getEmail().isEmpty()) {
                sendEmail(contactInfo.getEmail(), notification.getMessage());
                deliveryDetails = "Email envoyé à " + contactInfo.getEmail();
                success = true;
            } else if (contactInfo.getPhoneNumber() != null && !contactInfo.getPhoneNumber().isEmpty()) {
                sendSms(contactInfo.getPhoneNumber(), notification.getMessage());
                deliveryDetails = "SMS envoyé à " + contactInfo.getPhoneNumber();
                success = true;
            } else if (contactInfo.getPushToken() != null && !contactInfo.getPushToken().isEmpty()) {
                sendPushNotification(contactInfo.getPushToken(), notification.getMessage());
                deliveryDetails = "Push notification envoyée";
                success = true;
            } else {
                log.warn("Aucune info de contact valide pour {}", notification.getDestinataireId());
                deliveryDetails = "Aucune info de contact trouvée";
            }

            notificationService.handleDeliveryStatusUpdate(UUID.fromString(notification.getId()), success, deliveryDetails);

            if (success) {
                log.info("Notification {} envoyée avec succès : {}", notification.getId(), deliveryDetails);
            } else {
                log.error("Échec d'envoi pour {} : {}", notification.getId(), deliveryDetails);
                retrySending(notification, 3); // Ajout d'un retry en cas d'échec
            }

        } catch (Exception e) {
            log.error("Erreur d'envoi pour {} : {}", notification.getId(), e.getMessage(), e);
            notificationService.handleDeliveryStatusUpdate(UUID.fromString(notification.getId()), false, "Erreur: " + e.getMessage());
        }
    }

    private void retrySending(Notification notification, int attempts) {
        int retryCount = 0;
        while (retryCount < attempts) {
            try {
                sendNotification(notification);
                log.info("Retry {} réussi pour notification {}", retryCount, notification.getId());
                return;
            } catch (Exception e) {
                log.warn("Retry {} échoué pour {} : {}", retryCount, notification.getId(), e.getMessage());
                retryCount++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}
            }
        }
        log.error("Échec définitif après {} retries : {}", attempts, notification.getId());
    }

    private void sendEmail(String email, String message) {
        log.debug("Simulant l'envoi d'email à {}: {}", email, message);
    }

    private void sendSms(String phoneNumber, String message) {
        log.debug("Simulant l'envoi de SMS à {}: {}", phoneNumber, message);
    }

    private void sendPushNotification(String pushToken, String message) {
        log.debug("Simulant l'envoi de Push Notification à {}: {}", pushToken, message);
    }
}
