package com.emsi.foodallergyai.Models;

import java.util.List;

public class Allergen {
    private String id;
    private String name;
    private String description;
    private List<String> severityLevels;
    private List<String> alternativeNames;

    // Required empty constructor for Firebase
    public Allergen() {
    }

    public Allergen(String id, String name, String description, List<String> severityLevels, List<String> alternativeNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.severityLevels = severityLevels;
        this.alternativeNames = alternativeNames;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSeverityLevels() {
        return severityLevels;
    }

    public void setSeverityLevels(List<String> severityLevels) {
        this.severityLevels = severityLevels;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }
}
