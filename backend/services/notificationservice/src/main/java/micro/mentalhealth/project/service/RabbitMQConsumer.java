package micro.mentalhealth.project.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import micro.mentalhealth.project.config.RabbitMQConfig;
import micro.mentalhealth.project.dto.*; // Importe toutes les classes statiques de EventPayloads

@Component
public class RabbitMQConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final NotificationService notificationService;

    public RabbitMQConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_BOOKED_QUEUE)
    public void listenAppointmentBookedEvents(AppointmentBookedEvent event) {
        log.info("Received Appointment Booked Event for appointment ID: {}", event.getAppointmentId());
        notificationService.handleAppointmentBookedEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CONFIRMED_QUEUE)
    public void listenAppointmentConfirmedEvents(AppointmentConfirmedEvent event) {
        log.info("Received Appointment Confirmed Event for appointment ID: {}", event.getAppointmentId());
        notificationService.handleAppointmentConfirmedEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CANCELLED_QUEUE)
    public void listenAppointmentCancelledEvents(AppointmentCancelledEvent event) {
        log.info("Received Appointment Cancelled Event for appointment ID: {}", event.getAppointmentId());
        notificationService.handleAppointmentCancelledEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.PROGRAM_APPOINTMENTS_CANCELLED_QUEUE)
    public void listenProgramAppointmentsCancelledEvents(ProgramAppointmentsCancelledEvent event) {
        log.info("Received Program Appointments Cancelled Event for Program ID: {}", event.getProgramId());
        notificationService.handleProgramAppointmentsCancelledEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void listenPaymentEvents(PaymentReceivedEvent event) { // Utilisation d'un type plus spécifique si l'événement est toujours PaymentReceivedEvent
        log.info("Received Payment Event for transaction ID: {}", event.getTransactionId());
        notificationService.handlePaymentReceivedEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.FEEDBACK_QUEUE)
    public void listenFeedbackEvents(FeedbackSubmittedEvent event) {
        log.info("Received Feedback Event for feedback ID: {}", event.getFeedbackId());
        notificationService.handleFeedbackSubmittedEvent(event);
    }
}