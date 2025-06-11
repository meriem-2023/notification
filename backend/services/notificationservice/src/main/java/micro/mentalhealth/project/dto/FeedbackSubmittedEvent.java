package micro.mentalhealth.project.dto;

import java.util.UUID;

public class FeedbackSubmittedEvent {
    private UUID feedbackId;
    private UUID submitterId;
    private UUID targetId;
    private String feedbackContent;
    private int rating;

    public UUID getFeedbackId() { return feedbackId; }
    public void setFeedbackId(UUID feedbackId) { this.feedbackId = feedbackId; }
    public UUID getSubmitterId() { return submitterId; }
    public void setSubmitterId(UUID submitterId) { this.submitterId = submitterId; }
    public UUID getTargetId() { return targetId; }
    public void setTargetId(UUID targetId) { this.targetId = targetId; }
    public String getFeedbackContent() { return feedbackContent; }
    public void setFeedbackContent(String feedbackContent) { this.feedbackContent = feedbackContent; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    // Supposons que le thérapeute ciblé est "targetId"
    public UUID getTherapistId() {
        return targetId;
    }

    // Renvoie le contenu du feedback (commentaire)
    public String getComment() {
        return feedbackContent;
    }
}
