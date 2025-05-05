package com.emsi.foodallergyai.connection;

import android.util.Log;

import com.emsi.foodallergyai.Models.Allergen;
import com.emsi.foodallergyai.Models.Product;
import com.emsi.foodallergyai.Models.ScanHistory;
import com.emsi.foodallergyai.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";

    // Database reference
    private final DatabaseReference mDatabase;

    // Database paths
    private static final String USERS_PATH = "users";
    private static final String ALLERGENS_PATH = "allergens";
    private static final String PRODUCTS_PATH = "products";
    private static final String SCAN_HISTORY_PATH = "scan_history";

    // Singleton instance
    private static FirebaseHelper instance;

    // Private constructor for singleton pattern
    private FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Get singleton instance
    public static synchronized FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    // ===== USER METHODS =====

    // Add or update a user
    public void saveUser(User user, OnCompleteListener<Void> listener) {
        mDatabase.child(USERS_PATH).child(user.getUserId()).setValue(user)
                .addOnCompleteListener(listener);
    }

    // Get a user by ID
    public void getUserById(String userId, ValueEventListener listener) {
        mDatabase.child(USERS_PATH).child(userId).addListenerForSingleValueEvent(listener);
    }

    // ===== ALLERGEN METHODS =====

    // Get all allergens
    public void getAllAllergens(ValueEventListener listener) {
        mDatabase.child(ALLERGENS_PATH).addListenerForSingleValueEvent(listener);
    }

    // Get an allergen by ID
    public void getAllergenById(String allergenId, ValueEventListener listener) {
        mDatabase.child(ALLERGENS_PATH).child(allergenId).addListenerForSingleValueEvent(listener);
    }

    // ===== PRODUCT METHODS =====

    // Add or update a product
    public void saveProduct(Product product, OnCompleteListener<Void> listener) {
        mDatabase.child(PRODUCTS_PATH).child(product.getBarcode()).setValue(product)
                .addOnCompleteListener(listener);
    }

    // Get a product by barcode
    public void getProductByBarcode(String barcode, ValueEventListener listener) {
        mDatabase.child(PRODUCTS_PATH).child(barcode).addListenerForSingleValueEvent(listener);
    }

    // ===== SCAN HISTORY METHODS =====

    // Add a new scan history entry
    public void addScanHistory(ScanHistory scanHistory, OnCompleteListener<Void> listener) {
        // If scanId is not provided, generate a new one
        if (scanHistory.getScanId() == null || scanHistory.getScanId().isEmpty()) {
            String scanId = mDatabase.child(SCAN_HISTORY_PATH).push().getKey();
            scanHistory.setScanId(scanId);
        }

        mDatabase.child(SCAN_HISTORY_PATH).child(scanHistory.getScanId()).setValue(scanHistory)
                .addOnCompleteListener(listener);
    }

    // Get scan history for a user
    public void getUserScanHistory(String userId, ValueEventListener listener) {
        mDatabase.child(SCAN_HISTORY_PATH)
                .orderByChild("userId")
                .equalTo(userId)
                .addListenerForSingleValueEvent(listener);
    }

    // ===== HELPER METHODS =====

    // Convert DataSnapshot to User object
    public static User snapshotToUser(DataSnapshot snapshot) {
        return snapshot.getValue(User.class);
    }

    // Convert DataSnapshot to Allergen object
    public static Allergen snapshotToAllergen(DataSnapshot snapshot) {
        return snapshot.getValue(Allergen.class);
    }

    // Convert DataSnapshot to Product object
    public static Product snapshotToProduct(DataSnapshot snapshot) {
        return snapshot.getValue(Product.class);
    }

    // Convert DataSnapshot to ScanHistory object
    public static ScanHistory snapshotToScanHistory(DataSnapshot snapshot) {
        return snapshot.getValue(ScanHistory.class);
    }

    // Convert DataSnapshot to List of Allergens
    public static List<Allergen> snapshotToAllergenList(DataSnapshot snapshot) {
        List<Allergen> allergens = new ArrayList<>();
        for (DataSnapshot allergenSnapshot : snapshot.getChildren()) {
            Allergen allergen = snapshotToAllergen(allergenSnapshot);
            if (allergen != null) {
                allergens.add(allergen);
            }
        }
        return allergens;
    }

    // Convert DataSnapshot to List of Products
    public static List<Product> snapshotToProductList(DataSnapshot snapshot) {
        List<Product> products = new ArrayList<>();
        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
            Product product = snapshotToProduct(productSnapshot);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    // Convert DataSnapshot to List of ScanHistory
    public static List<ScanHistory> snapshotToScanHistoryList(DataSnapshot snapshot) {
        List<ScanHistory> scanHistories = new ArrayList<>();
        for (DataSnapshot scanSnapshot : snapshot.getChildren()) {
            ScanHistory scanHistory = snapshotToScanHistory(scanSnapshot);
            if (scanHistory != null) {
                scanHistories.add(scanHistory);
            }
        }
        return scanHistories;
    }

    // Check if a product contains allergens that a user is allergic to
    public static List<String> checkAllergenMatch(Product product, User user) {
        List<String> matchedAllergens = new ArrayList<>();

        if (product.getAllergens() != null && user.getAllergies() != null) {
            for (String productAllergen : product.getAllergens()) {
                if (user.getAllergies().contains(productAllergen)) {
                    matchedAllergens.add(productAllergen);
                }
            }
        }

        return matchedAllergens;
    }

    // Determine severity based on matched allergens and user profile
    public static String determineSeverity(List<String> matchedAllergens, User user) {
        if (matchedAllergens.isEmpty()) {
            return "safe";
        } else if (matchedAllergens.size() >= 2) {
            return "severe";
        } else {
            return "moderate";
        }
        // Note: In a real app, you would have a more sophisticated algorithm
        // that takes into account the specific allergens and their severity levels
    }
}
