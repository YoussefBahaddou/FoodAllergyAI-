package com.emsi.foodallergyai;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.emsi.foodallergyai.connection.DatabaseInitializer;
import com.emsi.foodallergyai.connection.FirebaseHelper;
import com.emsi.foodallergyai.Models.Allergen;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize Firebase helper
        firebaseHelper = FirebaseHelper.getInstance();

        // Check and initialize database if needed
        new DatabaseInitializer().checkAndInitializeDatabase();

        // Test database connection
        testDatabaseConnection();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Test database connection by retrieving allergens
    private void testDatabaseConnection() {
        firebaseHelper.getAllAllergens(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Allergen> allergens = FirebaseHelper.snapshotToAllergenList(snapshot);
                Log.d(TAG, "Retrieved " + allergens.size() + " allergens from database");

                if (!allergens.isEmpty()) {
                    // Database connection successful
                    Toast.makeText(MainActivity.this,
                            "Connected to database: " + allergens.size() + " allergens found",
                            Toast.LENGTH_SHORT).show();

                    // Log the first allergen details
                    if (allergens.size() > 0) {
                        Allergen firstAllergen = allergens.get(0);
                        Log.d(TAG, "First allergen: " + firstAllergen.getName() +
                                " - " + firstAllergen.getDescription());
                    }
                } else {
                    // No allergens found, database might be empty
                    Toast.makeText(MainActivity.this,
                            "Connected to database but no allergens found",
                            Toast.LENGTH_SHORT).show();

                    // Reinitialize database
                    new DatabaseInitializer().checkAndInitializeDatabase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(MainActivity.this,
                        "Database connection error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
