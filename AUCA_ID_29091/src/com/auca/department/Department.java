package com.auca.department;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Department implements Identifiable, Registrable {
    private String departmentId;
    private String name;
    private String headName;
    private String building;
    private BigDecimal budget;
    private LocalDate establishedDate;

    public Department(String departmentId, String name, String headName, String building,
                      BigDecimal budget, LocalDate establishedDate) {
        this.departmentId = departmentId;
        this.name = name;
        this.headName = headName;
        this.building = building;
        this.budget = budget;
        this.establishedDate = establishedDate;
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHeadName() { return headName; }
    public void setHeadName(String headName) { this.headName = headName; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
    public LocalDate getEstablishedDate() { return establishedDate; }
    public void setEstablishedDate(LocalDate establishedDate) { this.establishedDate = establishedDate; }

    @Override
    public String getId() { return departmentId; }

    @Override
    public String getDisplayName() { return name; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (departmentId == null || departmentId.trim().length() < MIN_ID_LENGTH || departmentId.trim().length() > MAX_ID_LENGTH)
            throw new ValidationException("Department id must be 3-30 characters.");
        if (name == null || name.trim().length() < MIN_NAME_LENGTH || name.trim().length() > MAX_NAME_LENGTH)
            throw new ValidationException("Department name must be 2-60 characters.");
        if (headName == null || headName.trim().isEmpty())
            throw new ValidationException("Head of department is required.");
        if (building == null || building.trim().isEmpty())
            throw new ValidationException("Building is required.");
        if (budget == null || budget.compareTo(BigDecimal.ZERO) < 0)
            throw new ValidationException("Budget cannot be negative.");
        if (establishedDate == null || establishedDate.isAfter(LocalDate.now()))
            throw new ValidationException("Established date must be a valid past date.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== DEPARTMENT ===");
        System.out.println("Department ID  : " + departmentId);
        System.out.println("Name           : " + name);
        System.out.println("Head           : " + headName);
        System.out.println("Building       : " + building);
        System.out.println("Budget         : " + budget);
        System.out.println("Established    : " + establishedDate);
    }

    @Override
    public String getRegistrationSummary() {
        return departmentId + " | " + name + " | " + headName + " | " + building;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
