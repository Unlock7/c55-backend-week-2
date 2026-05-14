package com.hyfacademy.model;

import com.hyfacademy.service.Reportable;

public class SelfPacedCourse extends Course implements Reportable {

    private int estimatedHours;

    public SelfPacedCourse(String courseName, int maxStudents, int estimatedHours) {
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
        System.out.println("══════════════════════════════════════════");
        System.out.println("  COURSE REPORT — Self-Paced");
        System.out.println("══════════════════════════════════════════");
        System.out.println("  ID          : " + getCourseId());
        System.out.println("  Name        : " + getCourseName());
        System.out.println("  Capacity    : " + capacityStatus());
        System.out.println("  Est. Hours  : " + estimatedHours);
        System.out.println("──────────────────────────────────────────");
        System.out.println("  STUDENT PROGRESS");
        System.out.println("──────────────────────────────────────────");

        for (int i = 0; i < getEnrolledCount(); i++) {
            Student s = students[i];
            int progress = studentProgress[i];

            System.out.printf("  %-8s %-20s %4d%%   %s%n",
                    s.getUserId(),
                    s.getName(),
                    progress,
                    progressBar(progress)
            );
        }

        System.out.println("══════════════════════════════════════════");
        return "";
    }
}

