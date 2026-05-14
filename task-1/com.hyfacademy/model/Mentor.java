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

       public void assignToCourse(Course course) {
           if (courseCount >= 3) {
               throw new EnrolmentException(
                       "Mentor already assigned to maximum of 3 courses");
           }
           for (int i = 0; i < courseCount; i++) {
               if (assignedCourses[i].getCourseId().equals(course.getCourseId())) {
                   throw new EnrolmentException(
                           "Mentor already assigned to course: " + course.getCourseName());
               }
           }
           assignedCourses[courseCount++] = course;
       }
        public Course[] getAssignedCourses() {
        return assignedCourses;
        }
        public String getExpertise() {
        return expertise;
        }
       }

