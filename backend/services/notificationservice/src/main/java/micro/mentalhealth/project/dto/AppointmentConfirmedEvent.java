package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentConfirmedEvent extends AppointmentEvent {

    public String getDate() {
        LocalDateTime dateTime = getAppointmentDateTime();
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
