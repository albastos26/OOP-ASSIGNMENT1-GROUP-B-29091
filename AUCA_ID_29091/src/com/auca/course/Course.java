package com.auca.course;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class Course implements Identifiable, Registrable {
    private String courseCode;
    private String title;
    private int credits;
    private Department department;
    private Course prerequisite;
    private int maxCapacity;

    public Course(String courseCode, String title, int credits, Department department,
                  Course prerequisite, int maxCapacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.department = department;
        this.prerequisite = prerequisite;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Course getPrerequisite() { return prerequisite; }
    public void setPrerequisite(Course prerequisite) { this.prerequisite = prerequisite; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    @Override
    public String getId() { return courseCode; }

    @Override
    public String getDisplayName() { return courseCode + " - " + title; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (courseCode == null || courseCode.trim().length() < MIN_ID_LENGTH || courseCode.trim().length() > MAX_ID_LENGTH)
            throw new ValidationException("Course code must be 3-30 characters.");
        if (title == null || title.trim().length() < MIN_NAME_LENGTH)
            throw new ValidationException("Course title is required.");
        if (credits < 1 || credits > 12)
            throw new ValidationException("Credits must be between 1 and 12.");
        if (department == null)
            throw new ValidationException("Department is required.");
        if (maxCapacity < 1)
            throw new ValidationException("Capacity must be at least 1.");
        if (prerequisite != null && prerequisite.getCourseCode().equals(courseCode))
            throw new ValidationException("A course cannot be its own prerequisite.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== COURSE ===");
        System.out.println("Code           : " + courseCode);
        System.out.println("Title          : " + title);
        System.out.println("Credits        : " + credits);
        System.out.println("Department     : " + (department != null ? department.getDisplayName() : "N/A"));
        System.out.println("Prerequisite   : " + (prerequisite != null ? prerequisite.getCourseCode() : "None"));
        System.out.println("Max capacity   : " + maxCapacity);
    }

    @Override
    public String getRegistrationSummary() {
        return courseCode + " | " + title + " | " + credits + " credits";
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
