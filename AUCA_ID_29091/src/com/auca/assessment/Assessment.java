package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {
    private String assessmentId;
    private Enrollment enrollment;
    private String type;
    private double score;
    private double maxScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String type, double score,
                      double maxScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.type = type;
        this.score = score;
        this.maxScore = maxScore;
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public double getMaxScore() { return maxScore; }
    public void setMaxScore(double maxScore) { this.maxScore = maxScore; }
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }

    public double getPercentage() { return maxScore == 0 ? 0 : score * 100.0 / maxScore; }

    @Override
    public String getId() { return assessmentId; }

    @Override
    public String getDisplayName() { return type + " " + assessmentId; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (assessmentId == null || assessmentId.trim().isEmpty())
            throw new ValidationException("Assessment id is required.");
        if (enrollment == null)
            throw new ValidationException("Enrollment is required.");
        if (type == null || type.trim().isEmpty())
            throw new ValidationException("Assessment type is required.");
        if (maxScore <= 0)
            throw new ValidationException("Maximum score must be positive.");
        if (score < 0 || score > maxScore)
            throw new ValidationException("Score must be between 0 and the maximum score.");
        if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Assessment date must be a valid past or present date.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== ASSESSMENT ===");
        System.out.println("Assessment ID  : " + assessmentId);
        System.out.println("Enrollment     : " + enrollment.getEnrollmentId());
        System.out.println("Type           : " + type);
        System.out.println("Score          : " + score + " / " + maxScore);
        System.out.println("Percentage     : " + String.format("%.1f", getPercentage()) + "%");
        System.out.println("Date           : " + assessmentDate);
    }

    @Override
    public String getRegistrationSummary() {
        return assessmentId + " | " + type + " | " + score + "/" + maxScore;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
