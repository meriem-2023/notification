package micro.mentalhealth.project.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notification.exchange";
    public static final String ROUTING_KEY = "notification.route";

    // Unique queue names for each type of notification
    public static final String QUEUE = "notification.queue";
    public static final String FEEDBACK_QUEUE = "feedback.notification.queue";
    public static final String PAYMENT_QUEUE = "payment.notification.queue";
    public static final String PROGRAM_APPOINTMENTS_CANCELLED_QUEUE = "program.cancelled.notification.queue";
    public static final String APPOINTMENT_CANCELLED_QUEUE = "appointment.cancelled.notification.queue";
    public static final String APPOINTMENT_CONFIRMED_QUEUE = "appointment.confirmed.notification.queue";
    public static final String APPOINTMENT_BOOKED_QUEUE = "appointment.booked.notification.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    // Define all queues
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue feedbackQueue() {
        return new Queue(FEEDBACK_QUEUE);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE);
    }

    @Bean
    public Queue programAppointmentsCancelledQueue() {
        return new Queue(PROGRAM_APPOINTMENTS_CANCELLED_QUEUE);
    }

    @Bean
    public Queue appointmentCancelledQueue() {
        return new Queue(APPOINTMENT_CANCELLED_QUEUE);
    }

    @Bean
    public Queue appointmentConfirmedQueue() {
        return new Queue(APPOINTMENT_CONFIRMED_QUEUE);
    }

    @Bean
    public Queue appointmentBookedQueue() {
        return new Queue(APPOINTMENT_BOOKED_QUEUE);
    }

    // Bind all queues to the exchange with appropriate routing keys
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding feedbackBinding(Queue feedbackQueue, TopicExchange exchange) {
        return BindingBuilder.bind(feedbackQueue).to(exchange).with("feedback.route");
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with("payment.route");
    }

    // Add other bindings as needed...
}