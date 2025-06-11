package micro.mentalhealth.project.dto;


import java.util.List;
import java.util.UUID;

public class ProgramAppointmentsCancelledEvent {
    private UUID programId;
    private UUID patientId;
    private UUID therapistId;
    private List<UUID> cancelledAppointmentIds;
    private String reason;

    public UUID getProgramId() { return programId; }
    public void setProgramId(UUID programId) { this.programId = programId; }
    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public UUID getTherapistId() { return therapistId; }
    public void setTherapistId(UUID therapistId) { this.therapistId = therapistId; }
    public List<UUID> getCancelledAppointmentIds() { return cancelledAppointmentIds; }
    public void setCancelledAppointmentIds(List<UUID> cancelledAppointmentIds) { this.cancelledAppointmentIds = cancelledAppointmentIds; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}

