package com.auca.feepayment;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FeePayment implements Identifiable, Registrable {
    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String method;
    private String referenceNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount, LocalDate paymentDate,
                      String method, String referenceNumber) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.referenceNumber = referenceNumber;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    @Override
    public String getId() { return paymentId; }

    @Override
    public String getDisplayName() { return "Payment " + paymentId; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (paymentId == null || paymentId.trim().isEmpty())
            throw new ValidationException("Payment id is required.");
        if (student == null)
            throw new ValidationException("Student is required.");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Amount must be strictly positive.");
        if (paymentDate == null || paymentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Payment date must be a valid past or present date.");
        if (method == null || method.trim().isEmpty())
            throw new ValidationException("Payment method is required.");
        if (referenceNumber == null || referenceNumber.trim().length() < 4)
            throw new ValidationException("Reference number must be at least 4 characters.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== FEE PAYMENT ===");
        System.out.println("Payment ID     : " + paymentId);
        System.out.println("Student        : " + student.getDisplayName());
        System.out.println("Amount         : " + amount);
        System.out.println("Date           : " + paymentDate);
        System.out.println("Method         : " + method);
        System.out.println("Reference      : " + referenceNumber);
    }

    @Override
    public String getRegistrationSummary() {
        return paymentId + " | " + student.getStudentNumber() + " | " + amount + " | " + method;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
