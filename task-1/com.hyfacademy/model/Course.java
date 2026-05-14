package com.hyfacademy.model;

import com.hyfacademy.exception.*;
import com.hyfacademy.service.Enrollable;
import com.hyfacademy.model.Student;

public abstract class Course implements Enrollable {

    private static int counter = 1;


    private String courseName;
    private String courseId;
    private int maxStudents;
    private int enrolledCount = 0;


    private Student[] students;
    private int[] studentProgress;


    public Course(String courseName, int maxStudents) {
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.courseId = String.format("CRS-%03d", counter++);


        this.students = new Student[maxStudents];
        this.studentProgress = new int[maxStudents];
    }


    public String getCourseName() { return
            courseName;
    }
    public String getCourseId() {
        return courseId;
    }
    public int getMaxStudents() {
        return maxStudents;
    }
    public int getEnrolledCount() {
        return enrolledCount;
    }

    @Override
    public void enrol(Student student) {
        if (isFull()) {
            throw new CourseFullException(courseName, maxStudents);
        }


        for (int i = 0; i < enrolledCount; i++) {
            if (students[i].getUserId().equals(student.getUserId())) {
                throw new AlreadyEnrolledException(student.getName(), courseName);
            }
        }


        students[enrolledCount] = student;
        studentProgress[enrolledCount] = 0;
        enrolledCount++;


        student.enrol(this);
    }

    public void updateProgress(Student student, int progress) {
        if (progress < 0 || progress > 100) {
            throw new InvalidProgressException(progress);
        }

        for (int i = 0; i < enrolledCount; i++) {
            if (students[i].getUserId().equals(student.getUserId())) {
                studentProgress[i] = progress;
                return;
            }
        }
        throw new EnrolmentException("Student " + student.getName() + " is not enrolled in this course.");
    }

    public int getStudentProgress(Student student) {
        for (int i = 0; i < enrolledCount; i++) {
            if (students[i].getUserId().equals(student.getUserId())) {
                return studentProgress[i];
            }
        }
        throw new EnrolmentException("Progress not found. Student not enrolled.");
    }

    @Override
    public boolean isFull() {
        return enrolledCount >= maxStudents;
    }


    public abstract String getCourseType();
    public abstract String getScheduleInfo();


    @Override
    public String toString() {

        return String.format("[%s] %s (%s) | Enrolled: %d/%d",
                courseId, courseName, getCourseType(), enrolledCount, maxStudents);
    }
}