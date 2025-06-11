package micro.mentalhealth.project.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentBookedEvent extends AppointmentEvent {

    public AppointmentBookedEvent() {
        super();
    }

    /**
     * Retourne la date du rendez-vous formatée en chaîne (ex: "2025-06-11 14:30")
     */
    public String getDate() {
        LocalDateTime dateTime = getAppointmentDateTime();
        if (dateTime == null) {
            return null;
        }
        // Format standard : "yyyy-MM-dd HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
