package micro.mentalhealth.project.dto;

import java.util.UUID;

public class PaymentReceivedEvent extends PaymentEvent {

    public UUID getPayerId() {
        // userId dans PaymentEvent correspond au payeur
        return getUserId();
    }
}
