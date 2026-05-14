package com.hyfacademy.model;

import com.hyfacademy.service.Reportable;

public class SelfPacedCourse extends Course implements Reportable {
    private int estimatedHours;

    public  SelfPacedCourse (String courseName, int maxStudents, int estimatedHours) {
        super(courseName, maxStudents);
        this.estimatedHours = estimatedHours;
    }

    @Override
    public String getCourseType() {
        return "Self-Paced";
    }

    @Override
    public String getScheduleInfo() {
        return "Estimated: " + estimatedHours + " hours — complete at your own pace";
    }
    @Override
    public String generateReport() {
        return "Course: " + getCourseName() + "\n" +
                "Type: " + getCourseType() + "\n" +
                "Estimated Hours: " + estimatedHours + "\n" +
                "Status: " + capacityStatus();
    }

    public int getEstimatedHours(){
        return estimatedHours;
    }

    }
