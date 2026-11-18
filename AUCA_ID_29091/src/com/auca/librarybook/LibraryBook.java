package com.auca.librarybook;

import com.auca.exceptions.BookUnavailableException;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class LibraryBook implements Identifiable, Registrable {
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String isbn, String title, String author, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public void borrowCopy() throws BookUnavailableException {
        if (availableCopies <= 0)
            throw new BookUnavailableException("No copies of '" + title + "' are available.");
        availableCopies--;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies)
            availableCopies++;
    }

    @Override
    public String getId() { return isbn; }

    @Override
    public String getDisplayName() { return title; }

    @Override
    public boolean isActive() { return totalCopies > 0; }

    @Override
    public void validate() throws ValidationException {
        if (isbn == null || !isbn.matches("[0-9\\-]{10,17}"))
            throw new ValidationException("ISBN must be 10-17 digits or hyphens.");
        if (title == null || title.trim().isEmpty())
            throw new ValidationException("Title is required.");
        if (author == null || author.trim().isEmpty())
            throw new ValidationException("Author is required.");
        if (totalCopies < 1)
            throw new ValidationException("Total copies must be at least 1.");
        if (availableCopies < 0 || availableCopies > totalCopies)
            throw new ValidationException("Available copies out of range.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== LIBRARY BOOK ===");
        System.out.println("ISBN           : " + isbn);
        System.out.println("Title          : " + title);
        System.out.println("Author         : " + author);
        System.out.println("Copies         : " + availableCopies + " / " + totalCopies);
    }

    @Override
    public String getRegistrationSummary() {
        return isbn + " | " + title + " | " + author + " | " + availableCopies + "/" + totalCopies;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
