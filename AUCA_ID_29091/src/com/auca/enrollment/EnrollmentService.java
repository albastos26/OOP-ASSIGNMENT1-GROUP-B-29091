package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.ValidationException;
import com.auca.student.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<>();
    private int counter = 1;

    public Enrollment enroll(Student student, Course course)
            throws PrerequisiteMissingException, ValidationException {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(student.getId())
                    && e.getCourse().getCourseCode().equals(course.getCourseCode())
                    && !Enrollment.STATUS_WITHDRAWN.equals(e.getStatus())) {
                throw new DuplicateRecordException(student.getDisplayName() + " is already enrolled in " + course.getCourseCode());
            }
        }
        Course prerequisite = course.getPrerequisite();
        if (prerequisite != null && !hasCompleted(student, prerequisite)) {
            throw new PrerequisiteMissingException(student.getDisplayName() + " has not completed prerequisite "
                    + prerequisite.getCourseCode() + " for " + course.getCourseCode());
        }
        long active = enrollments.stream()
                .filter(e -> e.getCourse().getCourseCode().equals(course.getCourseCode()) && e.isActive())
                .count();
        if (active >= course.getMaxCapacity())
            throw new ValidationException("Course " + course.getCourseCode() + " is full.");
        Enrollment enrollment = new Enrollment("ENR-" + String.format("%04d", counter++), student, course,
                LocalDate.now(), Enrollment.STATUS_ACTIVE, 0);
        enrollment.validate();
        enrollments.add(enrollment);
        return enrollment;
    }

    public boolean hasCompleted(Student student, Course course) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(student.getId())
                    && e.getCourse().getCourseCode().equals(course.getCourseCode())
                    && Enrollment.STATUS_COMPLETED.equals(e.getStatus())) {
                return true;
            }
        }
        return false;
    }

    public List<Enrollment> getEnrollments() { return enrollments; }
}
