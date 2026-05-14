package com.hyfacademy.model;

import com.hyfacademy.exception.AlreadyEnrolledException;
import com.hyfacademy.exception.CourseFullException;
import com.hyfacademy.exception.EnrolmentException;
import com.hyfacademy.exception.InvalidProgressException;
import com.hyfacademy.service.Enrollable;

public abstract class Course implements Enrollable {

    private static int counter = 1;

    private String courseName;
    private String courseId;
    private int maxStudents;
    private int enrolledCount;

    protected Student[] students;
    protected int[] studentProgress;

    public Course(String courseName, int maxStudents) {
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.courseId = String.format("CRS-%03d", counter++);

        this.students = new Student[maxStudents];
        this.studentProgress = new int[maxStudents];
        this.enrolledCount = 0;
    }


    public String getCourseName() {
        return courseName;
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

    public Student[] getStudents() {
        return students;
    }

    public int[] getStudentProgressArray() {
        return studentProgress;
    }


    @Override
    public boolean isFull() {
        return enrolledCount >= maxStudents;
    }

    @Override
    public void enrol(Student student) throws EnrolmentException {

        if (isFull()) {
            throw new CourseFullException(courseName, maxStudents);
        }


        for (int i = 0; i < enrolledCount; i++) {
            if (students[i].getUserId().equals(student.getUserId())) {
                throw new AlreadyEnrolledException(
                        student.getName(),
                        courseName
                );
            }
        }


        students[enrolledCount] = student;
        studentProgress[enrolledCount] = 0;
        enrolledCount++;

        student.enrol(this);
    }


    public void updateProgress(Student student, int progress)
            throws EnrolmentException, InvalidProgressException {

        if (progress < 0 || progress > 100) {
            throw new InvalidProgressException(progress);
        }

        int index = findStudentIndex(student);
        if (index == -1) {
            throw new EnrolmentException(
                    student.getName() + " is not enrolled in '" + courseName + "'"
            );
        }

        studentProgress[index] = progress;
    }

    public int getStudentProgress(Student student) throws EnrolmentException {
        int index = findStudentIndex(student);
        if (index == -1) {
            throw new EnrolmentException(
                    student.getName() + " is not enrolled in '" + courseName + "'"
            );
        }
        return studentProgress[index];
    }

    private int findStudentIndex(Student student) {
        for (int i = 0; i < enrolledCount; i++) {
            if (students[i].getUserId().equals(student.getUserId())) {
                return i;
            }
        }
        return -1;
    }

    protected String progressBar(int progress) {
        long filled = Math.round(progress / 10.0);
        int empty = (int)(10 - filled);

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filled; i++) bar.append("█");
        for (int i = 0; i < empty; i++) bar.append("░");
        return bar.toString();
    }

    public abstract String getCourseType();
    public abstract String getScheduleInfo();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) | Enrolled: %d/%d",
                courseId,
                courseName,
                getCourseType(),
                enrolledCount,
                maxStudents
        );
    }
}

