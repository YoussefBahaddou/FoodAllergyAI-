package com.emsi.foodallergyai.connection;

import android.util.Log;

import com.emsi.foodallergyai.Models.Allergen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public class DatabaseInitializer {
    private static final String TAG = "DatabaseInitializer";

    // Database reference
    private final DatabaseReference mDatabase;

    public DatabaseInitializer() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Check if database needs initialization and initialize if needed
    public void checkAndInitializeDatabase() {
        mDatabase.child("allergens").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If allergens node is empty, initialize the database
                if (!snapshot.exists() || !snapshot.hasChildren()) {
                    Log.d(TAG, "Database is empty, initializing with default data");
                    initializeDatabase();
                } else {
                    Log.d(TAG, "Database already initialized");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database check failed", error.toException());
            }
        });
    }

    // Initialize database with default data
    private void initializeDatabase() {
        // Initialize allergens
        initializeAllergens();

        // You could also initialize some sample products or users here
    }

    // Initialize allergens data
    private void initializeAllergens() {
        // Peanut
        List<String> peanutSeverity = Arrays.asList("mild", "moderate", "severe");
        List<String> peanutAltNames = Arrays.asList("groundnut", "arachis hypogaea", "monkey nut");
        Allergen peanut = new Allergen("peanut", "Peanut",
                "A common legume allergen that can cause severe reactions",
                peanutSeverity, peanutAltNames);

        // Milk
        List<String> milkSeverity = Arrays.asList("mild", "moderate", "severe");
        List<String> milkAltNames = Arrays.asList("dairy", "lactose", "whey", "casein", "cow's milk");
        Allergen milk = new Allergen("milk", "Milk",
                "Dairy allergen from cows that affects many people with lactose intolerance",
                milkSeverity, milkAltNames);

        // Gluten
        List<String> glutenSeverity = Arrays.asList("mild", "moderate", "severe");
        List<String> glutenAltNames = Arrays.asList("wheat", "rye", "barley", "malt", "triticale");
        Allergen gluten = new Allergen("gluten", "Gluten",
                "Protein found in certain grains that affects people with celiac disease",
                glutenSeverity, glutenAltNames);

        // Save allergens to database
        mDatabase.child("allergens").child("peanut").setValue(peanut);
        mDatabase.child("allergens").child("milk").setValue(milk);
        mDatabase.child("allergens").child("gluten").setValue(gluten);

        // Add more allergens as needed
    }
}
