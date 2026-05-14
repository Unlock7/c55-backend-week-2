package com.hyfacademy.model;

public abstract class  User {
    private String name;
    private String email;
    private String userId;

    public User(String name, String email, String userId){
        this.name = name;
        this.email = email;
        this.userId = userId;

    }

    public abstract String getRole();

    public String getSummary(){
        return String.format("[%s] %s | %s", getRole(), name, email);
    }

    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getUserId() {

        return userId;
    }

    @Override
    public String toString() {

        return getSummary();
    }
}
