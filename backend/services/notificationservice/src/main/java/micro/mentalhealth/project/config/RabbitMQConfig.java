package micro.mentalhealth.project.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notification.exchange";
    public static final String ROUTING_KEY = "notification.route";
    public static final String QUEUE = "notification.queue";
    public static final String FEEDBACK_QUEUE ="notification.queue" ;
    public static final String PAYMENT_QUEUE ="notification.queue" ;
    public static final String PROGRAM_APPOINTMENTS_CANCELLED_QUEUE ="notification.queue" ;
    public static final String APPOINTMENT_CANCELLED_QUEUE = "notification.queue";
    public static final String APPOINTMENT_CONFIRMED_QUEUE ="notification.queue" ;
    public static final String APPOINTMENT_BOOKED_QUEUE = "notification.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
