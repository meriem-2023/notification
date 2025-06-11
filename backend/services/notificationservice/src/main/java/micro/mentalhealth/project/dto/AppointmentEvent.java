package micro.mentalhealth.project.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentEvent {
    private UUID appointmentId;
    private UUID patientId;
    private UUID therapistId;
    private LocalDateTime appointmentDateTime;
    private String title;
    private String sessionLink;
    private String location;

    public AppointmentEvent() {}

    // Getters et Setters
    public UUID getAppointmentId() { return appointmentId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }
    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public UUID getTherapistId() { return therapistId; }
    public void setTherapistId(UUID therapistId) { this.therapistId = therapistId; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSessionLink() { return sessionLink; }
    public void setSessionLink(String sessionLink) { this.sessionLink = sessionLink; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

