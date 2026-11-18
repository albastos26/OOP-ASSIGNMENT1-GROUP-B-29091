package com.auca.bookloan;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private LibraryBook book;
    private Person borrower;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public BookLoan(String loanId, LibraryBook book, Person borrower, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.book = book;
        this.borrower = borrower;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }
    public LibraryBook getBook() { return book; }
    public Person getBorrower() { return borrower; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public boolean isReturned() { return returnDate != null; }

    public void markReturned(LocalDate date) {
        if (!isReturned()) {
            this.returnDate = date;
            book.returnCopy();
        }
    }

    public long daysOverdue() {
        LocalDate end = returnDate != null ? returnDate : LocalDate.now();
        return Math.max(0, ChronoUnit.DAYS.between(dueDate, end));
    }

    @Override
    public String getId() { return loanId; }

    @Override
    public String getDisplayName() { return "Loan " + loanId; }

    @Override
    public boolean isActive() { return !isReturned(); }

    @Override
    public void validate() throws ValidationException {
        if (loanId == null || loanId.trim().isEmpty())
            throw new ValidationException("Loan id is required.");
        if (book == null)
            throw new ValidationException("Book is required.");
        if (borrower == null)
            throw new ValidationException("Borrower is required.");
        if (loanDate == null || loanDate.isAfter(LocalDate.now()))
            throw new ValidationException("Loan date must be a valid past or present date.");
        if (dueDate == null || dueDate.isBefore(loanDate))
            throw new ValidationException("Due date cannot be before the loan date.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== BOOK LOAN ===");
        System.out.println("Loan ID        : " + loanId);
        System.out.println("Book           : " + book.getTitle());
        System.out.println("Borrower       : " + borrower.getDisplayName());
        System.out.println("Loan date      : " + loanDate);
        System.out.println("Due date       : " + dueDate);
        System.out.println("Returned       : " + (isReturned() ? returnDate.toString() : "NO"));
    }

    @Override
    public String getRegistrationSummary() {
        return loanId + " | " + book.getIsbn() + " | " + borrower.getId() + " | due " + dueDate;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
