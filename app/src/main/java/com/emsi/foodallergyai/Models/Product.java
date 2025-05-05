package com.emsi.foodallergyai.Models;

import java.util.List;

public class Product {
    private String barcode;
    private String name;
    private String brand;
    private List<String> ingredients;
    private List<String> allergens;

    // Required empty constructor for Firebase
    public Product() {
    }

    public Product(String barcode, String name, String brand, List<String> ingredients, List<String> allergens) {
        this.barcode = barcode;
        this.name = name;
        this.brand = brand;
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    // Getters and setters
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }
}
