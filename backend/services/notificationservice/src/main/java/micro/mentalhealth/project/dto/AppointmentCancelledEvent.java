package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentCancelledEvent extends AppointmentEvent {
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        LocalDateTime dateTime = getAppointmentDateTime();
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
