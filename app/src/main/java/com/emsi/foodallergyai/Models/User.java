package com.emsi.foodallergyai.Models;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String email;
    private List<String> allergies;

    // Required empty constructor for Firebase
    public User() {
    }

    public User(String userId, String name, String email, List<String> allergies) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.allergies = allergies;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
