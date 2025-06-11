package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReadCallbackRequest {
    private UUID notificationId;
    private LocalDateTime readTimestamp;

    public UUID getNotificationId() { return notificationId; }
    public void setNotificationId(UUID notificationId) { this.notificationId = notificationId; }
    public LocalDateTime getReadTimestamp() { return readTimestamp; }
    public void setReadTimestamp(LocalDateTime readTimestamp) { this.readTimestamp = readTimestamp; }
}
