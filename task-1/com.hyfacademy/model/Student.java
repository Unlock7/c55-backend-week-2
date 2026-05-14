package com.hyfacademy.model;
import com.hyfacademy.exception.*;

import com.hyfacademy.model.User;
import com.hyfacademy.model.Course;
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
    public void enrol(Course course) {

        if (courseCount >= 5) {
            throw new EnrolmentException("Student course list full (max 5 courses allowed).");
        }

        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourses[i] != null &&
                    enrolledCourses[i].getCourseId().equals(course.getCourseId())) {
                throw new EnrolmentException("Already enrolled in: " + course.getCourseName());
            }
        }

        enrolledCourses[courseCount++] = course;
    }

    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public int getProgress(String courseName) {
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourses[i].getCourseName().equalsIgnoreCase(courseName)) {
                return enrolledCourses[i].getStudentProgress(this.getUserId()userId());
            }
        }
        throw new EnrolmentException("Student is not enrolled in course: " + courseName);
    }
}