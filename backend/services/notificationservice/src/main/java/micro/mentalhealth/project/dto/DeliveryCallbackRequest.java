package micro.mentalhealth.project.dto;


import java.util.UUID;

public class DeliveryCallbackRequest {
    private UUID notificationId;
    private boolean success;
    private String externalMessageId;
    private String details;

    public UUID getNotificationId() { return notificationId; }
    public void setNotificationId(UUID notificationId) { this.notificationId = notificationId; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getExternalMessageId() { return externalMessageId; }
    public void setExternalMessageId(String externalMessageId) { this.externalMessageId = externalMessageId; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}

