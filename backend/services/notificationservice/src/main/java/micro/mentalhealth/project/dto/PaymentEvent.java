package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentEvent {
    private UUID transactionId;
    private UUID userId;
    private double amount;
    private String currency;
    private LocalDateTime transactionDate;

    public PaymentEvent() {}

    public UUID getTransactionId() { return transactionId; }
    public void setTransactionId(UUID transactionId) { this.transactionId = transactionId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
}
