package com.hyfacademy.model;


public class LiveCohortCourse extends Course {

    private String startDate;
    private String endDate;
    private Mentor mentor;

    public LiveCohortCourse (String courseName, int maxStudents, String startDate, String endDate){
        super(courseName, maxStudents);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getCourseType() {
        return "Live Cohort";
    }

    @Override
    public String getScheduleInfo(){
        String mentorName = (mentor != null) ? mentor.getName() : "No mentor assigned";
        return startDate + " to " + endDate + " | Mentor: " + mentorName;
    }

    public void assignMentor(Mentor mentor) {
        this.mentor = mentor;
        mentor.assignToCourse(this);
    }

    public Mentor getMentor() {
        return mentor;
    }

    public String getStartDate() {
        return startDate;

    }
    public String getEndDate() {
        return endDate;
    }

}
