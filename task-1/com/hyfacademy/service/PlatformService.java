package com.hyfacademy.service;

import com.hyfacademy.exception.*;
import com.hyfacademy.model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlatformService {

    private Course[] courses = new Course[10];
    private int courseCount = 0;

    private Student[] students = new Student[20];
    private int studentCount = 0;

    private Mentor[] mentors = new Mentor[5];
    private int mentorCount = 0;

    private Scanner scanner = new Scanner(System.in);


    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("Choose an option: ");
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
            } finally {
                scanner.nextLine(); // clear buffer
            }

            switch (choice) {
                case 1 -> addCourse();
                case 2 -> addStudent();
                case 3 -> addMentor();
                case 4 -> enrolStudent();
                case 5 -> updateProgress();
                case 6 -> viewAllCourses();
                case 7 -> viewCourseReport();
                case 8 -> viewAllStudents();
                case 9 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Error: Invalid option. Try again.");
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       HYF ACADEMY COURSE PLATFORM        ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("  1. Add course");
        System.out.println("  2. Add student");
        System.out.println("  3. Add mentor");
        System.out.println("  4. Enrol student in course");
        System.out.println("  5. Update student progress");
        System.out.println("  6. View all courses");
        System.out.println("  7. View course report");
        System.out.println("  8. View all students");
        System.out.println("  9. Exit");
        System.out.println("══════════════════════════════════════════");
    }

    private void addCourse() {
        if (courseCount >= courses.length) {
            System.out.println("Error: Cannot add more courses (limit reached).");
            return;
        }

        System.out.print("Course type (1 = Self-Paced, 2 = Live Cohort): ");
        int type;

        try {
            type = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid type.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        System.out.print("Course name: ");
        String name = scanner.nextLine();

        System.out.print("Max students: ");
        int max;
        try {
            max = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Max students must be a number.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        if (type == 1) {
            System.out.print("Estimated hours: ");
            int hours;
            try {
                hours = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Estimated hours must be a number.");
                scanner.nextLine();
                return;
            }
            scanner.nextLine();

            courses[courseCount++] = new SelfPacedCourse(name, max, hours);

        } else if (type == 2) {
            System.out.print("Start date (YYYY-MM-DD): ");
            String start = scanner.nextLine();
            System.out.print("End date (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            courses[courseCount++] = new LiveCohortCourse(name, max, start, end);

        } else {
            System.out.println("Error: Unknown course type.");
            return;
        }

        System.out.println("Course added.");
    }


    private void addStudent() {
        if (studentCount >= students.length) {
            System.out.println("Error: Cannot add more students (limit reached).");
            return;
        }

        System.out.print("Student name: ");
        String name = scanner.nextLine();
        System.out.print("Student email: ");
        String email = scanner.nextLine();

        Student s = new Student(name, email);
        students[studentCount++] = s;

        System.out.println("Student created with ID: " + s.getUserId());
    }


    private void addMentor() {
        if (mentorCount >= mentors.length) {
            System.out.println("Error: Cannot add more mentors (limit reached).");
            return;
        }

        System.out.print("Mentor name: ");
        String name = scanner.nextLine();
        System.out.print("Mentor email: ");
        String email = scanner.nextLine();
        System.out.print("Expertise: ");
        String expertise = scanner.nextLine();

        Mentor m = new Mentor(name, email, expertise);
        mentors[mentorCount++] = m;

        System.out.println("Mentor created with ID: " + m.getUserId());
    }


    private Student findStudentById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getUserId().equalsIgnoreCase(id)) {
                return students[i];
            }
        }
        return null;
    }

    private Course findCourseById(String id) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId().equalsIgnoreCase(id)) {
                return courses[i];
            }
        }
        return null;
    }

    private void enrolStudent() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Course ID: ");
        String courseId = scanner.nextLine();

        Student s = findStudentById(studentId);
        Course c = findCourseById(courseId);

        if (s == null) {
            System.out.println("Error: Student not found.");
            return;
        }
        if (c == null) {
            System.out.println("Error: Course not found.");
            return;
        }

        try {
            c.enrol(s);
            System.out.println("Student enrolled successfully.");
        } catch (CourseFullException | AlreadyEnrolledException e) {
            System.out.println("Error " + e.getMessage());
        } catch (EnrolmentException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private void updateProgress() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Course ID: ");
        String courseId = scanner.nextLine();

        Student s = findStudentById(studentId);
        Course c = findCourseById(courseId);

        if (s == null) {
            System.out.println("Error: Student not found.");
            return;
        }
        if (c == null) {
            System.out.println("Error: Course not found.");
            return;
        }

        System.out.print("Progress (0–100): ");
        int progress;

        try {
            progress = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Progress must be a number.");
            scanner.nextLine();
            return;
        } finally {
            scanner.nextLine();
        }

        try {
            c.updateProgress(s, progress);
            System.out.println("Progress updated.");
        } catch (InvalidProgressException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EnrolmentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllCourses() {
        System.out.println("══════════════════════════════════════════════════════════════════");
        System.out.println("  ID        NAME                      TYPE           CAPACITY");
        System.out.println("══════════════════════════════════════════════════════════════════");

        for (int i = 0; i < courseCount; i++) {
            System.out.println(courses[i]); // polymorphic toString()
        }

        System.out.println("══════════════════════════════════════════════════════════════════");
    }

    private void viewCourseReport() {
        System.out.print("Course ID: ");
        String courseId = scanner.nextLine();

        Course c = findCourseById(courseId);
        if (c == null) {
            System.out.println(" Course not found.");
            return;
        }

        if (c instanceof Reportable r) {
            r.generateReport();
        } else {
            System.out.println("This course does not support reporting.");
        }
    }

    private void viewAllStudents() {
        System.out.println("══════════════════════════════════════════");
        System.out.println("  STUDENTS");
        System.out.println("══════════════════════════════════════════");

        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            System.out.printf(" %-8s %-20s Courses: %d%n",
                    s.getUserId(),
                    s.getName(),
                    s.getCourseCount());
        }

        System.out.println("══════════════════════════════════════════");
    }
}

