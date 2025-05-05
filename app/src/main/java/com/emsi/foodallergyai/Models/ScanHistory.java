package com.emsi.foodallergyai.Models;

import java.util.List;

public class ScanHistory {
    private String scanId;
    private String userId;
    private long timestamp;
    private String productName;
    private List<String> ingredients;
    private List<String> detectedAllergens;
    private String severity;

    // Required empty constructor for Firebase
    public ScanHistory() {
    }

    public ScanHistory(String scanId, String userId, long timestamp, String productName,
                       List<String> ingredients, List<String> detectedAllergens, String severity) {
        this.scanId = scanId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.productName = productName;
        this.ingredients = ingredients;
        this.detectedAllergens = detectedAllergens;
        this.severity = severity;
    }

    // Getters and setters
    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDetectedAllergens() {
        return detectedAllergens;
    }

    public void setDetectedAllergens(List<String> detectedAllergens) {
        this.detectedAllergens = detectedAllergens;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
