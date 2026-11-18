package com.auca.main;

import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.enrollment.EnrollmentService;
import com.auca.exceptions.BookUnavailableException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private static final Map<String, Registrable> registry = new LinkedHashMap<>();

    private static <T extends Registrable & com.auca.interfaces.Identifiable> T register(T item)
            throws ValidationException {
        item.validate();
        if (registry.containsKey(item.getId()))
            throw new DuplicateRecordException("Duplicate record: " + item.getId());
        registry.put(item.getId(), item);
        return item;
    }

    private static Registrable find(String id) {
        Registrable r = registry.get(id);
        if (r == null)
            throw new RecordNotFoundException("No record found with id " + id);
        return r;
    }

    public static void main(String[] args) {
        try {
            Department cs = register(new Department("DEP-CS", "Computer Science", "Dr. Mukamana",
                    "Block A", new BigDecimal("50000000"), LocalDate.of(2005, 9, 1)));
            Department mis = register(new Department("DEP-MIS", "Management Information Systems", "Dr. Habimana",
                    "Block B", new BigDecimal("35000000"), LocalDate.of(2008, 9, 1)));

            Course prog1 = register(new Course("CSC101", "Programming I", 4, cs, null, 2));
            Course prog2 = register(new Course("CSC201", "Programming II", 4, cs, prog1, 2));
            Course db = register(new Course("MIS210", "Database Systems", 3, mis, null, 30));

            Classroom room = register(new Classroom("101", "Block A", 60, 1, true));

            Teacher teacher = register(new Teacher("TCH-001", "Jean", "Uwase", "jean.uwase@auca.ac.rw",
                    "0788123456", LocalDate.of(1980, 3, 14), "MEM-T01", cs, LocalDate.of(2015, 9, 1),
                    "ACTIVE", "j.uwase@auca.ac.rw", "N/A", "EMP-100", "Software Engineering",
                    LocalDate.of(2015, 9, 1), new BigDecimal("1500000"), "A-12", "FULL_TIME"));

            Administrator admin = register(new Administrator("ADM-001", "Grace", "Ineza", "grace.ineza@auca.ac.rw",
                    "0788654321", LocalDate.of(1985, 6, 2), "STF-010", "Registrar", "Registry",
                    LocalDate.of(2018, 1, 15), 3, true));

            Student alice = register(new Student("STD-001", "Alice", "Mutesi", "alice@auca.ac.rw",
                    "0788000001", LocalDate.of(2003, 5, 20), "MEM-S01", cs, LocalDate.of(2024, 9, 1),
                    "ACTIVE", "alice.m@auca.ac.rw", "Jean Uwase", "S-2024-001", "BSc Computer Science",
                    LocalDate.of(2024, 9, 1), 2, "250788111222", true));
            Student bob = register(new Student("STD-002", "Bob", "Niyonzima", "bob@auca.ac.rw",
                    "0788000002", LocalDate.of(2002, 11, 8), "MEM-S02", mis, LocalDate.of(2024, 9, 1),
                    "ACTIVE", "bob.n@auca.ac.rw", "Jean Uwase", "S-2024-002", "BSc Information Systems",
                    LocalDate.of(2024, 9, 1), 2, "250788333444", false));

            teacher.printDetails();
            System.out.println();
            admin.printDetails();
            System.out.println();
            alice.printDetails();
            System.out.println();
            room.printDetails();
            System.out.println();

            EnrollmentService service = new EnrollmentService();
            Enrollment e1 = service.enroll(alice, prog1);
            System.out.println("Enrolled: " + e1);

            try {
                service.enroll(alice, prog2);
            } catch (PrerequisiteMissingException ex) {
                System.out.println("Prerequisite check: " + ex.getMessage());
            }

            e1.complete(78.5);
            Assessment exam = new Assessment("ASM-001", e1, "Final Exam", 78.5, 100, LocalDate.now());
            exam.validate();
            exam.printDetails();

            Enrollment e2 = service.enroll(alice, prog2);
            System.out.println("Enrolled: " + e2);

            try {
                service.enroll(alice, prog2);
            } catch (DuplicateRecordException ex) {
                System.out.println("Duplicate check: " + ex.getMessage());
            }

            service.enroll(bob, db);

            FeePayment payment = new FeePayment("PAY-001", alice, new BigDecimal("450000"),
                    LocalDate.now(), "MOBILE_MONEY", "MM123456");
            payment.validate();
            payment.printDetails();
            System.out.println();

            LibraryBook book = register(new LibraryBook("978-0134685991", "Effective Java", "Joshua Bloch", 1));
            BookLoan loan = new BookLoan("LN-001", book, alice, LocalDate.now(), LocalDate.now().plusDays(14));
            loan.validate();
            book.borrowCopy();
            loan.printDetails();

            try {
                book.borrowCopy();
            } catch (BookUnavailableException ex) {
                System.out.println("Library check: " + ex.getMessage());
            }

            loan.markReturned(LocalDate.now());
            System.out.println("After return: " + book);

            try {
                register(new Department("DEP-CS", "Duplicate Dept", "Someone", "Block C",
                        BigDecimal.TEN, LocalDate.of(2000, 1, 1)));
            } catch (DuplicateRecordException ex) {
                System.out.println("Registry check: " + ex.getMessage());
            }

            try {
                find("STD-999");
            } catch (RecordNotFoundException ex) {
                System.out.println("Lookup check: " + ex.getMessage());
            }

            try {
                register(new Student("STD-003", "Carl", "Bad", "c@auca.ac.rw", "0788000003",
                        LocalDate.of(2003, 1, 1), "MEM-S03", cs, LocalDate.of(2024, 9, 1), "ACTIVE",
                        "c@auca.ac.rw", "N/A", "S-2024-003", "BSc CS", LocalDate.of(2024, 9, 1), 12,
                        "123", false));
            } catch (ValidationException ex) {
                System.out.println("Validation check: " + ex.getMessage());
            }

            System.out.println();
            System.out.println("Registered records: " + registry.size());
            for (Registrable r : registry.values())
                System.out.println(" - " + r.getRegistrationSummary());
            System.out.println("Lookup STD-001: " + find("STD-001").getRegistrationSummary());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
    }
}
