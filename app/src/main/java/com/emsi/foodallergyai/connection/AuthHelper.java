package com.emsi.foodallergyai.connection;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {
    private final FirebaseAuth mAuth;

    // Singleton instance
    private static AuthHelper instance;

    // Private constructor for singleton pattern
    private AuthHelper() {
        mAuth = FirebaseAuth.getInstance();
    }

    // Get singleton instance
    public static synchronized AuthHelper getInstance() {
        if (instance == null) {
            instance = new AuthHelper();
        }
        return instance;
    }

    // Get current user
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Check if user is logged in
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    // Register new user
    public void registerUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    // Login user
    public void loginUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    // Logout user
    public void logoutUser() {
        mAuth.signOut();
    }

    // Get user ID
    public String getUserId() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }
}
