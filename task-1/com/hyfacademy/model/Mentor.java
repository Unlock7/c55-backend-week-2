package com.hyfacademy.model;

import com.hyfacademy.exception.EnrolmentException;

public class Mentor extends User {

    private static int counter = 1;

    private String expertise;
    private Course[] assignedCourses = new Course[3];
    private int courseCount = 0;

    public Mentor(String name, String email, String expertise) {
        super(name, email, String.format("COA-%03d", counter++));
        this.expertise = expertise;
    }

    @Override
    public String getRole() {
        return "MENTOR";
    }

    public void assignToCourse(Course course) throws EnrolmentException {
        if (courseCount >= 3) {
            throw new EnrolmentException(
                    getName() + " cannot be assigned to more than 3 courses"
            );
        }

        assignedCourses[courseCount++] = course;
    }

    public Course[] getAssignedCourses() {
        return assignedCourses;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public String getExpertise() {
        return expertise;
    }
}


