package com.hyfacademy.model;

import com.hyfacademy.exception.EnrolmentException;
import com.hyfacademy.service.Reportable;

public class LiveCohortCourse extends Course implements Reportable {

    private String startDate;
    private String endDate;
    private Mentor mentor;

    public LiveCohortCourse(String courseName, int maxStudents, String startDate, String endDate) {
        super(courseName, maxStudents);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getCourseType() {
        return "Live Cohort";
    }

    @Override
    public String getScheduleInfo() {
        String mentorName = (mentor != null ? mentor.getName() : "No mentor assigned");
        return startDate + " to " + endDate + " | Mentor: " + mentorName;
    }

    public void assignMentor(Mentor mentor) throws EnrolmentException {
        this.mentor = mentor;
        mentor.assignToCourse(this);
    }

    @Override
    public String generateReport() {

        System.out.println("══════════════════════════════════════════");
        System.out.println("  COURSE REPORT — Live Cohort");
        System.out.println("══════════════════════════════════════════");
        System.out.println("  ID          : " + getCourseId());
        System.out.println("  Name        : " + getCourseName());
        System.out.println("  Schedule    : " + startDate + " to " + endDate);
        System.out.println("  Mentor      : " + (mentor != null ? mentor.getName() : "None"));
        System.out.println("  Capacity    : " + capacityStatus());
        System.out.println("──────────────────────────────────────────");
        System.out.println("  STUDENT PROGRESS");
        System.out.println("──────────────────────────────────────────");

        int total = 0;

        for (int i = 0; i < getEnrolledCount(); i++) {
            Student s = students[i];
            int progress = studentProgress[i];
            total += progress;

            System.out.printf("  %-8s %-20s %4d%%   %s%n",
                    s.getUserId(),
                    s.getName(),
                    progress,
                    progressBar(progress)
            );
        }

        System.out.println("──────────────────────────────────────────");

        int avg = (getEnrolledCount() == 0) ? 0 : total / getEnrolledCount();
        System.out.println("  Avg Progress : " + avg + "%");
        System.out.println("══════════════════════════════════════════");

        return "";
    }
}
