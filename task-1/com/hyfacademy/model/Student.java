package com.hyfacademy.model;

import com.hyfacademy.exception.AlreadyEnrolledException;
import com.hyfacademy.exception.EnrolmentException;

public class Student extends User {

    private static int counter = 1;

    private Course[] enrolledCourses = new Course[5];
    private int courseCount = 0;

    public Student(String name, String email) {
        super(name, email, String.format("STU-%03d", counter++));
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    public void enrol(Course course) throws EnrolmentException {

        // Student limit
        if (courseCount >= 5) {
            throw new EnrolmentException(
                    getName() + " cannot enrol in more than 5 courses"
            );
        }

        // Already enrolled?
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourses[i].getCourseId().equals(course.getCourseId())) {
                throw new AlreadyEnrolledException(
                        getName(),
                        course.getCourseName()
                );
            }
        }

        enrolledCourses[courseCount++] = course;
    }

    public Course[] getCourses() {
        return enrolledCourses;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public int getProgress(String courseName) throws EnrolmentException {
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourses[i].getCourseName().equalsIgnoreCase(courseName)) {
                return enrolledCourses[i].getStudentProgress(this);
            }
        }

        throw new EnrolmentException(
                getName() + " is not enrolled in course '" + courseName + "'"
        );
    }
}

