package com.auca.abstracts;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName, String email,
                  String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public String getId() { return personId; }

    @Override
    public String getDisplayName() { return firstName + " " + lastName; }

    @Override
    public boolean isActive() { return true; }

    public abstract String getRoleDescription();

    @Override
    public String getRegistrationSummary() {
        return personId + " | " + getDisplayName() + " | " + email + " | " + phoneNumber;
    }

    @Override
    public void printDetails() {
        System.out.println("Person ID      : " + personId);
        System.out.println("First name     : " + firstName);
        System.out.println("Last name      : " + lastName);
        System.out.println("Email          : " + email);
        System.out.println("Phone          : " + phoneNumber);
        System.out.println("Date of birth  : " + dateOfBirth);
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
