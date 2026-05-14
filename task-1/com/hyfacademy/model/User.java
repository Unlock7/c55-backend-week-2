package com.hyfacademy.model;

public abstract class User {
    private static int userCounter = 1;

    protected String name;
    protected String email;
    protected String userId;

    public User(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getUserId() {
        return userId;
    }

    public abstract String getRole();

    public String getSummary() {
        return "[" + getRole() + "] " + name + " | " + email;
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
