package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_WITHDRAWN = "WITHDRAWN";

    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String status;
    private double finalGrade;

    public Enrollment(String enrollmentId, Student student, Course course, LocalDate enrollmentDate,
                      String status, double finalGrade) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.finalGrade = finalGrade;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getFinalGrade() { return finalGrade; }
    public void setFinalGrade(double finalGrade) { this.finalGrade = finalGrade; }

    public void complete(double grade) throws ValidationException {
        if (grade < 0 || grade > 100)
            throw new ValidationException("Grade must be between 0 and 100.");
        this.finalGrade = grade;
        this.status = STATUS_COMPLETED;
    }

    @Override
    public String getId() { return enrollmentId; }

    @Override
    public String getDisplayName() { return student.getDisplayName() + " in " + course.getCourseCode(); }

    @Override
    public boolean isActive() { return STATUS_ACTIVE.equals(status); }

    @Override
    public void validate() throws ValidationException {
        if (enrollmentId == null || enrollmentId.trim().isEmpty())
            throw new ValidationException("Enrollment id is required.");
        if (student == null)
            throw new ValidationException("Student is required.");
        if (course == null)
            throw new ValidationException("Course is required.");
        if (enrollmentDate == null || enrollmentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Enrollment date must be a valid past or present date.");
        if (!STATUS_ACTIVE.equals(status) && !STATUS_COMPLETED.equals(status) && !STATUS_WITHDRAWN.equals(status))
            throw new ValidationException("Status must be ACTIVE, COMPLETED or WITHDRAWN.");
        if (finalGrade < 0 || finalGrade > 100)
            throw new ValidationException("Grade must be between 0 and 100.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== ENROLLMENT ===");
        System.out.println("Enrollment ID  : " + enrollmentId);
        System.out.println("Student        : " + student.getDisplayName());
        System.out.println("Course         : " + course.getDisplayName());
        System.out.println("Date           : " + enrollmentDate);
        System.out.println("Status         : " + status);
        System.out.println("Final grade    : " + finalGrade);
    }

    @Override
    public String getRegistrationSummary() {
        return enrollmentId + " | " + student.getStudentNumber() + " | " + course.getCourseCode() + " | " + status;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
