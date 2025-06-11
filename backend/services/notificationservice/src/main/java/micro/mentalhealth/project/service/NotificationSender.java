package micro.mentalhealth.project.service;



import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async; // Pour un envoi asynchrone

import  micro.mentalhealth.project.model.Notification;
import  micro.mentalhealth.project.model.RecipientType;
import  micro.mentalhealth.project.dto.ContactInfo; // Importe la classe ContactInfo
import java.util.UUID;

@Component
public class NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);
    private final UserServiceFeignClient userServiceFeignClient;
    private final NotificationService notificationService; // Pour mettre à jour le statut

    public NotificationSender(UserServiceFeignClient userServiceFeignClient, NotificationService notificationService) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.notificationService = notificationService;
    }

    @Async // Permet à cette méthode de s'exécuter dans un thread séparé
    public void sendNotification(Notification notification) {
        log.info("Attempting to send notification {}: {}", notification.getId(), notification.getMessage());

        String deliveryDetails = "N/A";
        boolean success = false;

        try {
            // 1. Récupérer les infos de contact via FeignClient
            ContactInfo contactInfo = userServiceFeignClient.getContactInfo(notification.getDestinataireId(), notification.getRecipientType());

            // 2. Choisir le canal d'envoi (email, SMS, push)
            if (contactInfo.getEmail() != null && !contactInfo.getEmail().isEmpty()) {
                sendEmail(contactInfo.getEmail(), notification.getMessage());
                deliveryDetails = "Email sent to " + contactInfo.getEmail();
                success = true;
            } else if (contactInfo.getPhoneNumber() != null && !contactInfo.getPhoneNumber().isEmpty()) {
                sendSms(contactInfo.getPhoneNumber(), notification.getMessage());
                deliveryDetails = "SMS sent to " + contactInfo.getPhoneNumber();
                success = true;
            } else if (contactInfo.getPushToken() != null && !contactInfo.getPushToken().isEmpty()) {
                sendPushNotification(contactInfo.getPushToken(), notification.getMessage());
                deliveryDetails = "Push notification sent to device";
                success = true;
            } else {
                log.warn("No usable contact info found for destinataireId: {}", notification.getDestinataireId());
                deliveryDetails = "No contact info found";
                success = false;
            }

            // Mettre à jour le statut dans la base de données
            notificationService.handleDeliveryStatusUpdate(UUID.fromString(notification.getId()), success, deliveryDetails);

            if (success) {
                log.info("Notification {} sent successfully. Details: {}", notification.getId(), deliveryDetails);
            } else {
                log.error("Failed to send notification {}. Details: {}", notification.getId(), deliveryDetails);
            }

        } catch (Exception e) {
            log.error("Error while sending notification {}: {}", notification.getId(), e.getMessage(), e);
            deliveryDetails = "Error: " + e.getMessage();
            notificationService.handleDeliveryStatusUpdate(UUID.fromString(notification.getId()), false, deliveryDetails);
        }
    }

    private void sendEmail(String email, String message) {
        // Implémentation réelle d'envoi d'email (via un client SMTP, SendGrid, Mailgun, etc.)
        log.debug("Simulating sending email to {}: {}", email, message);
        // Ici, vous intégreriez un client d'envoi d'email
    }

    private void sendSms(String phoneNumber, String message) {
        // Implémentation réelle d'envoi de SMS (via Twilio, Nexmo, etc.)
        log.debug("Simulating sending SMS to {}: {}", phoneNumber, message);
        // Ici, vous intégreriez un client d'envoi de SMS
    }

    private void sendPushNotification(String pushToken, String message) {
        // Implémentation réelle d'envoi de push (via Firebase Cloud Messaging, OneSignal, etc.)
        log.debug("Simulating sending Push Notification to {}: {}", pushToken, message);
        // Ici, vous intégreriez un client d'envoi de push
    }
}
